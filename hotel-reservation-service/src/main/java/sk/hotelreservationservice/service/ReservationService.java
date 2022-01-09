package sk.hotelreservationservice.service;

import sk.hotelreservationservice.dto.*;

public interface ReservationService {

    RoomsDto addRooms(RoomsCreateDto roomsCreateDto);
    HotelDto addHotel(HotelCreateDto hotelCreateDto);
    BookingDto addBooking(BookingCreateDto bookingCreateDto);

}
