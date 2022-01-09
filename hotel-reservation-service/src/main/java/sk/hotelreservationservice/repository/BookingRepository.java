package sk.hotelreservationservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.hotelreservationservice.domain.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
