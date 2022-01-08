package sk.hotelnotificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.hotelnotificationservice.domain.NotificationType;

@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType,Long> {

    NotificationType findNotificationTypeByName(String name);

}
