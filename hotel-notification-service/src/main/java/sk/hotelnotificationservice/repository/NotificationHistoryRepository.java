package sk.hotelnotificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sk.hotelnotificationservice.domain.NotificationHistory;

import java.util.List;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {

    @Query(value = "select * from notification_history where notification_name = 'registration' and " +
            "DATEDIFF(arrival, CAST( GETDATE() AS Date )) <=2 and flag = 0", nativeQuery = true)
    List<NotificationHistory> findNotificationReservationHistory();
}
