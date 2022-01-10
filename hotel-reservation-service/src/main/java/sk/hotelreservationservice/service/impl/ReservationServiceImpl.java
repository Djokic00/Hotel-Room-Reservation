package sk.hotelreservationservice.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.hotelreservationservice.domain.Booking;
import sk.hotelreservationservice.domain.Hotel;
import sk.hotelreservationservice.domain.Rooms;
import sk.hotelreservationservice.dto.*;
import sk.hotelreservationservice.listener.helper.MessageHelper;
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
    private JmsTemplate jmsTemplate;
    private String incrementBookingDestination;
    private MessageHelper messageHelper;
    private String forwardClientBookingDestination;

    public ReservationServiceImpl(ReservationMapper reservationMapper,
                                  HotelRepository hotelRepository, RoomsRepository roomsRepository,
                                  BookingRepository bookingRepository,
                                  JmsTemplate jmsTemplate, @Value("${destination.incrementBooking}") String incrementBookingDestination,
                                  @Value("${destination.forwardClientBooking}") String forwardClientBookingDestination) {
        this.reservationMapper = reservationMapper;
        this.hotelRepository = hotelRepository;
        this.roomsRepository = roomsRepository;
        this.bookingRepository = bookingRepository;
        this.jmsTemplate = jmsTemplate;
        this.incrementBookingDestination = incrementBookingDestination;
        this.forwardClientBookingDestination = forwardClientBookingDestination;
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
        IncrementBookingDto incrementBookingDto = new IncrementBookingDto(booking.getUsername());
        jmsTemplate.convertAndSend(incrementBookingDestination, messageHelper.createTextMessage(incrementBookingDto));
        return reservationMapper.bookingToBookingDto(booking);
    }

    @Override
    public Integer unavailableRooms(BookingCreateDto bookingCreateDto) {
        Integer numberOfUnavailableRooms = roomsRepository.findUnavailableRoomsForBooking(bookingCreateDto.getHotelName(),
                bookingCreateDto.getCity(),bookingCreateDto.getArrival(),bookingCreateDto.getDeparture(),bookingCreateDto.getRoomType());
        return numberOfUnavailableRooms;
    }

    @Override
    public Integer availableRooms(BookingCreateDto bookingCreateDto) {
        Integer numberOfUnavailableRooms = roomsRepository.findUnavailableRoomsForBooking(bookingCreateDto.getHotelName(),
                bookingCreateDto.getCity(),bookingCreateDto.getArrival(),bookingCreateDto.getDeparture(),bookingCreateDto.getRoomType());
        Integer numberOfRooms = roomsRepository.findRoomsByType(bookingCreateDto.getRoomType()).getLastRoomNumber()
                - roomsRepository.findRoomsByType(bookingCreateDto.getRoomType()).getFirstRoomNumber() + 1 ;
        return numberOfRooms-numberOfUnavailableRooms;
    }

    @Override
    public void forwardClientAndBooking(ClientDto clientDto) {
        Booking booking = bookingRepository.findBookingByUsername(clientDto.getUsername());
        BookingClientDto bookingClientDto = new BookingClientDto();
        bookingClientDto.setArrival(booking.getArrival());
        bookingClientDto.setDeparture(booking.getDeparture());
        bookingClientDto.setCity(booking.getCity());
        bookingClientDto.setHotelName(booking.getHotelName());
        bookingClientDto.setRoomType(booking.getRooms().getType());
        bookingClientDto.setBirthday(clientDto.getBirthday());
        bookingClientDto.setEmail(clientDto.getEmail());
        bookingClientDto.setFirstName(clientDto.getFirstName());
        bookingClientDto.setLastName(clientDto.getLastName());
        bookingClientDto.setPassportNumber(clientDto.getPassportNumber());
        bookingClientDto.setNumberOfReservations(clientDto.getNumberOfReservations());
        bookingClientDto.setContact(clientDto.getContact());
        bookingClientDto.setUsername(clientDto.getUsername());

        jmsTemplate.convertAndSend(forwardClientBookingDestination, messageHelper.createTextMessage(bookingClientDto));

    }
}
