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


    @Override
    public ResponseEntity<Void> sendMail(ClientDto clientDto, String notificationName) {
        Notification notification = notificationRepository.findNotificationByName(notificationName);

        String content = notification.getMessage();

        content = content.replace("%firstname", clientDto.getFirstName());
        content = content.replace("%lastname", clientDto.getLastName());
        content = content.replace("%username", clientDto.getUsername());
        content = content.replace("%email", clientDto.getEmail());
//        content = content.replace("%firstname", clientDto.getFirstName());
//        content = content.replace("%lastname", clientDto.getLastName());
        String verifyURL = "http://localhost:8081/verify?token=" + clientDto.getVerificationCode();
        content = content.replace("%link", verifyURL);

        emailService.sendSimpleMessage(clientDto.getEmail(), notificationName, content);

        NotificationHistory notificationHistory = new NotificationHistory(clientDto.getEmail(), content, notificationName);
        notificationHistoryRepository.save(notificationHistory);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> sendReservationMail(BookingClientDto bookingClientDto, String notificationName) {
        Notification notificationType = notificationRepository.findNotificationByName(notificationName);
        String content = notificationType.getMessage();

        content = content.replace("%firstname", bookingClientDto.getFirstName());
        content = content.replace("%lastname", bookingClientDto.getLastName());
        content = content.replace("%username", bookingClientDto.getUsername());
        content = content.replace("%email", bookingClientDto.getEmail());
        //content = content.replace((CharSequence) "%arrival", (CharSequence) bookingClientDto.getArrival());
        //content = content.replace((CharSequence) "%departure", (CharSequence) bookingClientDto.getDeparture());
        content = content.replace("%city", bookingClientDto.getCity());
        content = content.replace("%hotelname", bookingClientDto.getHotelName());
        content = content.replace("%roomtype", bookingClientDto.getRoomType());
        content = content.replace("%email", bookingClientDto.getEmail());
        //content = content.replace((CharSequence) "%birthday", (CharSequence) bookingClientDto.getBirthday());
        //content = content.replace("%passportnumber", bookingClientDto.getPassportNumber());
        //content = content.replace("%contact", bookingClientDto.getContact());

        emailService.sendSimpleMessage(bookingClientDto.getEmail(), notificationName, content);

        //NotificationHistory notificationHistory = new NotificationHistory(bookingClientDto.getEmail(), content, notificationName);
        NotificationHistory notificationHistory = new NotificationHistory();
        notificationHistory.setEmail(bookingClientDto.getEmail());
        notificationHistory.setArrival(bookingClientDto.getArrival());
        notificationHistory.setNotificationName(notificationName);
        notificationHistory.setMessage(content);

        if (notificationName.equals("cancel reservation")) notificationHistory.setFlag(1);
        else notificationHistory.setFlag(0);
        notificationHistoryRepository.save(notificationHistory);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> sendReservationReminder(NotificationHistory notificationHistory) {

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


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
