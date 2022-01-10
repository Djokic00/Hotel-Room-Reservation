package sk.hotelnotificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.hotelnotificationservice.domain.Notification;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    Notification findNotificationByName(String name);
    Notification findNotificationById(Long id);

}
