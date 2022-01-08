package sk.hotelnotificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.hotelnotificationservice.domain.NotificationHistory;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {
}
