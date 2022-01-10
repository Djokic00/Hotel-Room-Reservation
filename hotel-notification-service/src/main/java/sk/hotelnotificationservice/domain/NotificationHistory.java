package sk.hotelnotificationservice.domain;

import javax.persistence.*;

@Entity
public class NotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String message;
    private String notificationName;
  //  private String date;


    public NotificationHistory(String email, String message, String notificationName) {
        this.email = email;
        this.message = message;
        this.notificationName = notificationName;

    }

    public NotificationHistory() {
    }

    public String getNotificationName() {
        return notificationName;
    }

    public void setNotificationName(String notificationName) {
        this.notificationName = notificationName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}