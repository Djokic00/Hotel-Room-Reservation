package sk.hotelreservationservice.service;

import sk.hotelreservationservice.dto.*;

public interface ReservationService {

    RoomsDto addRooms(RoomsCreateDto roomsCreateDto);
    HotelDto addHotel(HotelCreateDto hotelCreateDto);
    BookingDto addBooking(BookingCreateDto bookingCreateDto);
    Integer unavailableRooms(BookingCreateDto bookingCreateDto);
    Integer availableRooms(BookingCreateDto bookingCreateDto);
    void forwardClientAndBooking(ClientDto clientDto);
}
