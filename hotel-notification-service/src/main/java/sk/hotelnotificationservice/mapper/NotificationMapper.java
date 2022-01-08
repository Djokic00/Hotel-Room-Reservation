package sk.hotelnotificationservice.mapper;

import org.springframework.stereotype.Component;
import sk.hotelnotificationservice.domain.NotificationType;
import sk.hotelnotificationservice.dto.NotificationTypeCreateDto;
import sk.hotelnotificationservice.dto.NotificationTypeDto;
import sk.hotelnotificationservice.repository.NotificationTypeRepository;

@Component
public class NotificationMapper {

    private NotificationTypeRepository notificationTypeRepository;

    public NotificationMapper(NotificationTypeRepository notificationTypeRepository) {
        this.notificationTypeRepository = notificationTypeRepository;
    }

    public NotificationTypeDto notifTypeToNotifTypeDto(NotificationType notifType){

        NotificationTypeDto notificationTypeDto=new NotificationTypeDto();
        notificationTypeDto.setMessage(notifType.getMessage());
        notificationTypeDto.setName(notifType.getName());

        return notificationTypeDto;
    }

    public NotificationType notifTypeCreateDtoToNotifType(NotificationTypeCreateDto notificationTypeCreateDto){

        NotificationType notificationType=new NotificationType();
        notificationType.setMessage(notificationTypeCreateDto.getMessage());
        notificationType.setName(notificationTypeCreateDto.getName());

        return notificationType;
    }




}
