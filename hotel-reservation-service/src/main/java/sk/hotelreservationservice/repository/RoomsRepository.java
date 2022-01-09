package sk.hotelreservationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sk.hotelreservationservice.domain.Rooms;

import java.sql.Date;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Long> {

    Rooms findRoomsByType(String type);
//    @Query(
//            value = "SELECT * FROM Users u WHERE u.status = ?1",
//            nativeQuery = true)
//    Rooms findAvailableRooms();

    //https://www.baeldung.com/spring-data-jpa-query
    //https://stackoverflow.com/questions/24285118/show-available-rooms-between-to-dates-sql

    @Query(value = "select count(booking.id) from booking join rooms on booking.rooms_id = rooms.id where booking.hotel_name = ? and booking.city = ? and booking.departure >= to_date(?, 'yyyy-mm-dd')\n" +
            "  and arrival <= to_date(?, 'yyyy-mm-dd') and rooms.type = ? sort rooms.price", nativeQuery = true)
    Integer findUnavailableRoomsForBooking(String hotelName, String city, Date departure, Date arrival, String type);
}
