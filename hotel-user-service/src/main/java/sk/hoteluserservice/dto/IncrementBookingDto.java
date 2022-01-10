package sk.hoteluserservice.dto;

public class IncrementBookingDto {
    private String username;

    public IncrementBookingDto() {

    }

    public IncrementBookingDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
