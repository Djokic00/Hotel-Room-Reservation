package sk.hotelreservationservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class HotelCreateDto {


    @NotBlank
    private String city;
    @NotBlank
    private String description;
    @NotBlank
    private String hotelName;

    @NotNull
    private Integer numberOfRooms;

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

    @Override
    public String toString() {
        return "HotelCreateDto{" +
                "city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                '}';
    }
}
