package sk.hotelreservationservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.hotelreservationservice.domain.Hotel;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Hotel findHotelByHotelName(String hotelName);
    Hotel findHotelById(Long id);

}