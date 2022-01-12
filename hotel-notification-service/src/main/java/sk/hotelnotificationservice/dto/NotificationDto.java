package sk.hotelnotificationservice.dto;

public class NotificationDto {
    private String name;
    private String message;
    private String managerMassage;

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

    public String getManagerMassage() {
        return managerMassage;
    }

    public void setManagerMassage(String managerMassage) {
        this.managerMassage = managerMassage;
    }
}
