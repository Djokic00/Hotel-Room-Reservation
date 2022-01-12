package sk.hotelnotificationservice.service;

import org.springframework.http.ResponseEntity;
import sk.hotelnotificationservice.domain.NotificationHistory;
import sk.hotelnotificationservice.dto.*;

public interface NotificationService {

    NotificationDto addNotification(NotificationCreateDto notificationCreateDto);
    void deleteNotificationById(Long id);
    NotificationDto updateNotification(Long id, NotificationCreateDto notificationCreateDto);
    void sendMail(ClientDto clientDto, String notificationName);
    void sendReservationMail(BookingClientDto bookingClientDto, String notificationName);
    void sendReservationMailManager(BookingClientDto bookingClientDto, String notificationName);
    void sendReservationReminder(NotificationHistory notificationHistory);
    void sendResetPasswordMail(ClientDto clientDto, String notificationName);
}
