package sk.hotelreservationservice.domain;

import javax.persistence.*;

@Entity
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String price;
    private Integer firstRoomNumber;
    private Integer lastRoomNumber;
    //private Integer availableRooms;

    @ManyToOne(optional = false)
    private Hotel hotel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getFirstRoomNumber() {
        return firstRoomNumber;
    }

    public void setFirstRoomNumber(Integer firstRoomNumber) {
        this.firstRoomNumber = firstRoomNumber;
    }

    public Integer getLastRoomNumber() {
        return lastRoomNumber;
    }

    public void setLastRoomNumber(Integer lastRoomNumber) {
        this.lastRoomNumber = lastRoomNumber;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

//    public Integer getAvailableRooms() {
//        return availableRooms;
//    }
//
//    public void setAvailableRooms(Integer availableRooms) {
//        this.availableRooms = availableRooms;
//    }
}
