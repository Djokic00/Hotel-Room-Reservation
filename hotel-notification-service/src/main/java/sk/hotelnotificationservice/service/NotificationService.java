package sk.hotelnotificationservice.service;

import org.springframework.http.ResponseEntity;
import sk.hotelnotificationservice.domain.NotificationType;
import sk.hotelnotificationservice.dto.*;

public interface NotificationService {

    NotificationTypeDto addNotificationType(NotificationTypeCreateDto notificationTypeCreateDto);
    ResponseEntity<Void> sendMail(ClientDto clientDto, String notificationType);

    ResponseEntity<Void> sendReservationMail(BookingClientDto bookingClientDto, String notificationType);
}
