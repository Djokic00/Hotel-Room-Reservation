package sk.hotelnotificationservice.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sk.hotelnotificationservice.domain.NotificationHistory;
import sk.hotelnotificationservice.repository.NotificationHistoryRepository;
import sk.hotelnotificationservice.service.NotificationService;

import java.util.List;

@Component
public class SimpleScheduler{

    private NotificationService notificationService;
    private NotificationHistoryRepository notificationHistoryRepository;

    public SimpleScheduler(NotificationService notificationService, NotificationHistoryRepository notificationHistoryRepository) {
        this.notificationService = notificationService;
        this.notificationHistoryRepository = notificationHistoryRepository;
    }

    @Scheduled(fixedDelay = 600000, initialDelay = 600000)
    public void sendReservationReminder(){
        List<NotificationHistory> list = notificationHistoryRepository.findNotificationReservationHistory();

        for (NotificationHistory nh: list){
            notificationService.sendReservationReminder(nh);
        }
    }

}
