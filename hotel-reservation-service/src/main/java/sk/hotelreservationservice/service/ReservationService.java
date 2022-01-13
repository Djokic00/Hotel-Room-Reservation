package sk.hotelreservationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sk.hotelreservationservice.domain.Booking;
import sk.hotelreservationservice.dto.*;
import sk.hotelreservationservice.userservice.dto.ClientQueueDto;

public interface ReservationService {

    RoomsDto addRooms(RoomsCreateDto roomsCreateDto);
    HotelDto addHotel(HotelCreateDto hotelCreateDto);
    BookingDto addBooking(BookingCreateDto bookingCreateDto);
    BookingDto removeBooking(BookingCreateDto bookingCreateDto, Long bookingDd);
    Integer unavailableRooms(BookingCreateDto bookingCreateDto);
    Integer availableRooms(BookingCreateDto bookingCreateDto);
    void forwardClientAndBooking(ClientQueueDto clientQueueDto);

    Page<CommentDto> findAllByHotelId(Long hotelId, Pageable pageable);
    Page<RoomsDto> findAll(Pageable pageable);
    CommentDto addCommentOnHotel(Long hotelId, CommentCreateDto commentCreateDto);
}
