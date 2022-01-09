package sk.hotelreservationservice.domain;

import javax.persistence.*;

@Entity
public class Booking {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

       // private String client; kako izvuci ulogovanog korisnika?
       // private String price; racuna se broj_nocenja*cena*(100-popust)/100
        private String arrival;
        private String departure;

        @ManyToOne(optional = false)
        private Rooms rooms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
//
//    public String getClient() {
//        return client;
//    }
//
//    public void setClient(String client) {
//        this.client = client;
//    }

//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }

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

    public Rooms getRooms() {
        return rooms;
    }

    public void setRooms(Rooms rooms) {
        this.rooms = rooms;
    }
}
