package sk.hotelreservationservice.dto;

import javax.validation.constraints.NotBlank;

public class BookingCreateDto {
    @NotBlank
    private String arrival;
    @NotBlank
    private String departure;
    @NotBlank
    private String roomType;

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
