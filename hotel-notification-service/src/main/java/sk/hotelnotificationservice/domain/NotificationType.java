package sk.hotelnotificationservice.domain;

import javax.persistence.*;

@Entity
@Table(indexes = {@Index(columnList = "name", unique = true)})
public class NotificationType {

    @Id
    private String name;
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
