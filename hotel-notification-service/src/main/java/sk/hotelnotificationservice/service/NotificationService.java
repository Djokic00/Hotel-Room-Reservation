package sk.hotelnotificationservice.service;

import org.springframework.http.ResponseEntity;
import sk.hotelnotificationservice.domain.NotificationHistory;
import sk.hotelnotificationservice.dto.*;

public interface NotificationService {

    NotificationDto addNotification(NotificationCreateDto notificationCreateDto);
    void deleteNotificationById(Long id);
    NotificationDto updateNotification(Long id, NotificationCreateDto notificationCreateDto);
    ResponseEntity<Void> sendMail(ClientDto clientDto, String notificationName);
    ResponseEntity<Void> sendReservationMail(BookingClientDto bookingClientDto, String notificationName);
    ResponseEntity<Void> sendReservationReminder(NotificationHistory notificationHistory);
    ResponseEntity<Void> sendResetPasswordMail(ClientDto clientDto, String notificationName);
}
