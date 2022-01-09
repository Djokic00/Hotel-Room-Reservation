package sk.hotelreservationservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class HotelCreateDto {

    @NotBlank
    private String hotelName;
    @NotBlank
    private String description;
    @NotNull
    private Integer numberOfRooms;
    @NotBlank
    private String city;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
