package sk.hotelreservationservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sk.hotelreservationservice.domain.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findBookingByUsername(String username);
    Booking findBookingById(Long id);
    //@Query(value = "select * from booking where booking.username = ? order by id desc limit 1", nativeQuery = true)

    //SELECT TOP 1 * FROM Table ORDER BY ID DESC
    @Query(value = "select top 1 * from booking where booking.username = ? order by id desc", nativeQuery = true)
    Booking findLastBookingByUsername(String username);
}
