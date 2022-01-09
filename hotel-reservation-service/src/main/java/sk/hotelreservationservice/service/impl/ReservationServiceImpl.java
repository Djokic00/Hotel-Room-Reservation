package sk.hotelreservationservice.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.hotelreservationservice.domain.Booking;
import sk.hotelreservationservice.domain.Hotel;
import sk.hotelreservationservice.domain.Rooms;
import sk.hotelreservationservice.dto.*;
import sk.hotelreservationservice.mapper.ReservationMapper;
import sk.hotelreservationservice.repository.BookingRepository;
import sk.hotelreservationservice.repository.HotelRepository;
import sk.hotelreservationservice.repository.RoomsRepository;
import sk.hotelreservationservice.service.ReservationService;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private ReservationMapper reservationMapper;
    private HotelRepository hotelRepository;
    private RoomsRepository roomsRepository;
    private BookingRepository bookingRepository;

    public ReservationServiceImpl(ReservationMapper reservationMapper,
                                  HotelRepository hotelRepository, RoomsRepository roomsRepository,
                                  BookingRepository bookingRepository) {
        this.reservationMapper = reservationMapper;
        this.hotelRepository = hotelRepository;
        this.roomsRepository = roomsRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public RoomsDto addRooms(RoomsCreateDto roomsCreateDto) {
        Rooms rooms = reservationMapper.roomsCreateDtoToRooms(roomsCreateDto);
        roomsRepository.save(rooms);
        return reservationMapper.roomsToRoomsDto(rooms);

    }

    @Override
    public HotelDto addHotel(HotelCreateDto hotelCreateDto) {
        Hotel hotel = reservationMapper.hotelCreateDtoToHotel(hotelCreateDto);
        hotelRepository.save(hotel);
        return reservationMapper.hotelToHotelDto(hotel);
    }

    @Override
    public BookingDto addBooking(BookingCreateDto bookingCreateDto) {
        Booking booking = reservationMapper.bookingCreateDtoToBooking(bookingCreateDto);
        bookingRepository.save(booking);
        return reservationMapper.bookingToBookingDto(booking);
    }
}
