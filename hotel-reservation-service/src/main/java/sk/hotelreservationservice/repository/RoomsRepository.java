package sk.hotelreservationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sk.hotelreservationservice.domain.Rooms;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Long> {

    Rooms findRoomsByType(String type);
//    @Query(
//            value = "SELECT * FROM Users u WHERE u.status = ?1",
//            nativeQuery = true)
//    Rooms findAvailableRooms();

    //https://www.baeldung.com/spring-data-jpa-query
    //https://stackoverflow.com/questions/24285118/show-available-rooms-between-to-dates-sql

}
