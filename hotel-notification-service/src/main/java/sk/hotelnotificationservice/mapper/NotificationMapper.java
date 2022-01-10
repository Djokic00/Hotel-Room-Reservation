package sk.hotelnotificationservice.mapper;

import org.springframework.stereotype.Component;
import sk.hotelnotificationservice.domain.Notification;
import sk.hotelnotificationservice.dto.NotificationCreateDto;
import sk.hotelnotificationservice.dto.NotificationDto;
import sk.hotelnotificationservice.repository.NotificationRepository;

@Component
public class NotificationMapper {

    private NotificationRepository notificationRepository;

    public NotificationMapper(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationDto notificationToNotificationDto(Notification notification){
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setMessage(notification.getMessage());
        notificationDto.setName(notification.getName());
        return notificationDto;
    }

    public Notification notificationCreateDtoToNotification(NotificationCreateDto notificationCreateDto){

        Notification notificationType=new Notification();
        notificationType.setMessage(notificationCreateDto.getMessage());
        notificationType.setName(notificationCreateDto.getName());

        return notificationType;
    }




}
