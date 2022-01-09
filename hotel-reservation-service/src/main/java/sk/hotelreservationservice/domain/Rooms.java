package sk.hotelreservationservice.domain;

import javax.persistence.*;

@Entity
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String price;
    private Integer firstNo;
    private Integer lastNo;
    private Integer availableRooms;

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

    public Integer getFirstNo() {
        return firstNo;
    }

    public void setFirstNo(Integer firstNo) {
        this.firstNo = firstNo;
    }

    public Integer getLastNo() {
        return lastNo;
    }

    public void setLastNo(Integer lastNo) {
        this.lastNo = lastNo;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Integer getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(Integer availableRooms) {
        this.availableRooms = availableRooms;
    }
}
