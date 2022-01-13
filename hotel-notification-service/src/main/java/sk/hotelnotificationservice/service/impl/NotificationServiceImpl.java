package sk.hotelnotificationservice.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sk.hotelnotificationservice.domain.NotificationHistory;
import sk.hotelnotificationservice.domain.Notification;
import sk.hotelnotificationservice.dto.*;
import sk.hotelnotificationservice.mapper.NotificationMapper;
import sk.hotelnotificationservice.repository.NotificationHistoryRepository;
import sk.hotelnotificationservice.repository.NotificationRepository;
import sk.hotelnotificationservice.service.EmailService;
import sk.hotelnotificationservice.service.NotificationService;

import javax.transaction.Transactional;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;
    private NotificationMapper notificationMapper;
    private EmailService emailService;
    private NotificationHistoryRepository notificationHistoryRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationMapper notificationMapper,
                                   EmailService emailService,
                                   NotificationHistoryRepository notificationHistoryRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.emailService = emailService;
        this.notificationHistoryRepository = notificationHistoryRepository;
    }

    @Override
    public NotificationDto addNotification(NotificationCreateDto notificationCreateDto) {
        Notification notification = notificationMapper.notificationCreateDtoToNotification(notificationCreateDto);
        notificationRepository.save(notification);
        return notificationMapper.notificationToNotificationDto(notification);
    }

    @Override
    public NotificationDto updateNotification(Long id, NotificationCreateDto notificationCreateDto) {
        Notification notification = notificationRepository.findNotificationById(id);
        notification.setMessage(notificationCreateDto.getMessage());
        notification.setName(notificationCreateDto.getName());
        return notificationMapper.notificationToNotificationDto(notificationRepository.save(notification));
    }

    @Override
    public void deleteNotificationById(Long id) {
        notificationRepository.deleteById(id);
    }


//    @Override
//    public void sendMail(ClientDto clientDto, String notificationName) {
//        Notification notification = notificationRepository.findNotificationByName(notificationName);
//        String content = notification.getMessage();
//
//        content = content.replace("%firstname", clientDto.getFirstName());
//        content = content.replace("%lastname", clientDto.getLastName());
//        content = content.replace("%username", clientDto.getUsername());
//        content = content.replace("%email", clientDto.getEmail());
////        content = content.replace("%firstname", clientDto.getFirstName());
////        content = content.replace("%lastname", clientDto.getLastName());
//        String verifyURL = "http://localhost:8081/verify?token=" + clientDto.getVerificationCode();
//        content = content.replace("%link", verifyURL);
//
//        emailService.sendSimpleMessage(clientDto.getEmail(), notificationName, content);
//
//        NotificationHistory notificationHistory = new NotificationHistory(clientDto.getEmail(), content, notificationName);
//        notificationHistoryRepository.save(notificationHistory);
//
//    }

    @Override
    public void sendVerificationMail(UserDto userDto, String notificationName) {
        Notification notification = notificationRepository.findNotificationByName(notificationName);
        String content = notification.getMessage();

        String verifyURL = "http://localhost:8081/verify?token=" + userDto.getVerificationCode();
        content = content.replace("%link", verifyURL);

        emailService.sendSimpleMessage(userDto.getEmail(), notificationName, content);

        NotificationHistory notificationHistory = new NotificationHistory(userDto.getEmail(), content, notificationName);
        notificationHistoryRepository.save(notificationHistory);
    }

    @Override
    public void sendReservationMail(BookingClientDto bookingClientDto, String notificationName) {
        Notification notification = notificationRepository.findNotificationByName(notificationName);
        String content = notification.getMessage();
        String managerContent = notification.getManagerMessage();

        content = content.replace("%firstname", bookingClientDto.getFirstName());
        content = content.replace("%lastname", bookingClientDto.getLastName());
        content = content.replace("%email", bookingClientDto.getEmail());
        content = content.replace("%arrival", bookingClientDto.getArrival().toString());
        content = content.replace("%departure", bookingClientDto.getDeparture().toString());
        content = content.replace("%city", bookingClientDto.getCity());
        content = content.replace("%hotelname", bookingClientDto.getHotelName());
        content = content.replace("%roomtype", bookingClientDto.getRoomType());
        content = content.replace("%email", bookingClientDto.getEmail());
        managerContent = managerContent.replace("%userId", String.valueOf(bookingClientDto.getUserId()));

        emailService.sendSimpleMessage(bookingClientDto.getEmail(), notificationName, content);
        emailService.sendSimpleMessage(bookingClientDto.getManagerEmail(), notificationName, managerContent);

        NotificationHistory notificationHistory = new NotificationHistory();
        notificationHistory.setEmail(bookingClientDto.getEmail());
        notificationHistory.setArrival(bookingClientDto.getArrival());
        notificationHistory.setNotificationName(notificationName);
        notificationHistory.setMessage(content);

        if (notificationName.equals("cancel reservation")) notificationHistory.setFlag(1);
        else notificationHistory.setFlag(0);
        notificationHistoryRepository.save(notificationHistory);

        NotificationHistory notificationHistoryManager = new NotificationHistory();
        notificationHistoryManager.setEmail(bookingClientDto.getManagerEmail());
        notificationHistoryManager.setNotificationName(notificationName);
        notificationHistoryManager.setMessage(managerContent);
        notificationHistoryManager.setFlag(1);

        notificationHistoryRepository.save(notificationHistoryManager);

    }


    @Override
    public void sendReservationReminder(NotificationHistory notificationHistory) {
        emailService.sendSimpleMessage(notificationHistory.getEmail(), "reservation reminder", notificationHistory.getMessage());
        notificationHistory.setFlag(1);
        notificationHistoryRepository.save(notificationHistory);

        NotificationHistory reminder = new NotificationHistory();
        reminder.setEmail(notificationHistory.getEmail());
        reminder.setArrival(notificationHistory.getArrival());
        reminder.setNotificationName("reservation reminder");
        reminder.setMessage(notificationHistory.getMessage());
        reminder.setFlag(1);

        notificationHistoryRepository.save(reminder);
    }

    @Override
    public void sendResetPasswordMail(UserDto userDto, String notificationName) {
        Notification notification = notificationRepository.findNotificationByName(notificationName);
        String content = notification.getMessage();
        content = content.replace("%username", userDto.getUsername());
        content = content.replace("%email", userDto.getEmail());
        emailService.sendSimpleMessage(userDto.getEmail(), notificationName, content);
        NotificationHistory notificationHistory = new NotificationHistory(userDto.getEmail(), content, notificationName);
        notificationHistoryRepository.save(notificationHistory);
    }
}
