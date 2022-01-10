package sk.hoteluserservice.dto;

public class ClientBookingDto {
    private String username;
    private Boolean increment;
    private Long bookingId;

    public ClientBookingDto() {

    }

    public ClientBookingDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getIncrement() {
        return increment;
    }

    public void setIncrement(Boolean increment) {
        this.increment = increment;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public String toString() {
        return "ClientBookingDto{" +
                "username='" + username + '\'' +
                ", increment=" + increment +
                ", bookingId=" + bookingId +
                '}';
    }
}
