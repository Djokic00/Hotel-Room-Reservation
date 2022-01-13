package sk.hotelreservationservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sk.hotelreservationservice.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByHotel_Id(Long productId, Pageable pageable);


    @Query(value = "select avg(hotel_rating) from comment where hotel_id = ?", nativeQuery = true)
    String findAverageRatingByHotelId(Long hotelId);


}
