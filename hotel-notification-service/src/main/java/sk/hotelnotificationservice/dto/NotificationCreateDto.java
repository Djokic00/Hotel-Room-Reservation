package sk.hotelnotificationservice.dto;

import javax.validation.constraints.NotNull;

public class NotificationCreateDto {

    @NotNull
    private String name;
    @NotNull
    private String message;
    @NotNull
    private String managerMessage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getManagerMessage() {
        return managerMessage;
    }

    public void setManagerMessage(String managerMessage) {
        this.managerMessage = managerMessage;
    }
}
