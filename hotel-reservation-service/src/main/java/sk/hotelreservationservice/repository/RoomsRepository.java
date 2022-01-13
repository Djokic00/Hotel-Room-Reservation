package sk.hotelreservationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sk.hotelreservationservice.domain.Rooms;

import java.awt.print.Pageable;
import java.sql.Date;
import java.util.List;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Long> {

    Rooms findRoomsByType(String type);

    @Query(value = "select * from rooms where rooms.hotel_name = ?", nativeQuery = true)
    List<Rooms> findAllByHotel(String hotel);
    //    @Query(
//            value = "SELECT * FROM Users u WHERE u.status = ?1",
//            nativeQuery = true)
//    Rooms findAvailableRooms();

    //https://www.baeldung.com/spring-data-jpa-query
    //https://stackoverflow.com/questions/24285118/show-available-rooms-between-to-dates-sql

    @Query(value = "select count(booking.id) from booking join rooms on booking.rooms_id = rooms.id where booking.hotel_name = ? and booking.city = ? and booking.departure >= to_date(?, 'yyyy-mm-dd')\n" +
            "  and arrival <= to_date(?, 'yyyy-mm-dd') and rooms.type = ?", nativeQuery = true)
    Integer findUnavailableRoomsForBooking(String hotelName, String city, Date departure, Date arrival, String type);

    @Query(value = "select price from rooms join hotel on rooms.hotel_id = hotel.id where hotel.hotel_name = ? and hotel.city = ? and rooms.type = ?", nativeQuery = true)
    String findPriceByHotelCityAndRoomType(String hotelName, String city, String roomType);
}
