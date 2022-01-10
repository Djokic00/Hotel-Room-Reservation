package sk.hotelreservationservice.service;

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
}
