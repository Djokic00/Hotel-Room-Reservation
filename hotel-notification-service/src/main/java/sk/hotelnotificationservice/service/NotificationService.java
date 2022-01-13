package sk.hotelnotificationservice.service;

import org.springframework.http.ResponseEntity;
import sk.hotelnotificationservice.domain.NotificationHistory;
import sk.hotelnotificationservice.dto.*;

public interface NotificationService {

    NotificationDto addNotification(NotificationCreateDto notificationCreateDto);
    void deleteNotificationById(Long id);
    NotificationDto updateNotification(Long id, NotificationCreateDto notificationCreateDto);
    void sendVerificationMail(UserDto userDto, String notificationName);
    //void sendRegistrationClientMail(ClientDto clientDto, String notificationName);
    //void sendRegistrationManagerMail(ManagerDto managerDto, String notificationName);
    void sendReservationMail(BookingClientDto bookingClientDto, String notificationName);
    void sendReservationReminder(NotificationHistory notificationHistory);
    void sendResetPasswordMail(UserDto userDto, String notificationName);
}
