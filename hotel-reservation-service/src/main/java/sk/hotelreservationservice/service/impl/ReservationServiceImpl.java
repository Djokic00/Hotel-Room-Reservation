package sk.hotelreservationservice.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
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
import sk.hotelreservationservice.userservice.UserServiceClientConfiguration;
import sk.hotelreservationservice.userservice.dto.ClientBookingDto;
import sk.hotelreservationservice.userservice.dto.ClientQueueDto;
import sk.hotelreservationservice.userservice.dto.ClientStatusDto;

import java.math.BigDecimal;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private ReservationMapper reservationMapper;
    private HotelRepository hotelRepository;
    private RoomsRepository roomsRepository;
    private BookingRepository bookingRepository;
    private JmsTemplate jmsTemplate;
    private String bookingDestination;
    private MessageHelper messageHelper;
    private String forwardClientBookingDestination;
    private RestTemplate userServiceRestTemplate;

    public ReservationServiceImpl(ReservationMapper reservationMapper,
                                  HotelRepository hotelRepository, RoomsRepository roomsRepository,
                                  BookingRepository bookingRepository,
                                  JmsTemplate jmsTemplate, @Value("${destination.bookingNumber}") String bookingDestination,
                                  @Value("${destination.forwardClientBooking}") String forwardClientBookingDestination,
                                  MessageHelper messageHelper,
                                  RestTemplate userServiceClientConfiguration) {
        this.reservationMapper = reservationMapper;
        this.hotelRepository = hotelRepository;
        this.roomsRepository = roomsRepository;
        this.bookingRepository = bookingRepository;
        this.jmsTemplate = jmsTemplate;
        this.bookingDestination = bookingDestination;
        this.forwardClientBookingDestination = forwardClientBookingDestination;
        this.messageHelper = messageHelper;
        this.userServiceRestTemplate = userServiceClientConfiguration;
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

        ResponseEntity<ClientStatusDto> discountDtoResponseEntity = userServiceRestTemplate.exchange("/user/" +
                bookingCreateDto.getUsername() + "/discount", HttpMethod.GET, null, ClientStatusDto.class);

//        //calculate price
//        BigDecimal price = Integer.parseInt(booking.getPrice()).divide(BigDecimal.valueOf(100))
//                .multiply(BigDecimal.valueOf(100 - discountDtoResponseEntity.getBody().getDiscount()));

        // Integer numberOfNights = booking.getDeparture() - booking.getArrival();

        Double newPrice = Double.parseDouble(booking.getPrice()) * (100 - discountDtoResponseEntity.getBody().getDiscount());

        //broj_nocenja*cena*(100-popust)/100

        booking.setPrice(String.valueOf(newPrice));
        bookingRepository.save(booking);
        ClientBookingDto clientBookingDto = new ClientBookingDto(booking.getUsername());
        clientBookingDto.setIncrement(true);
        clientBookingDto.setBookingId(booking.getId());
        jmsTemplate.convertAndSend(bookingDestination, messageHelper.createTextMessage(clientBookingDto));
        return reservationMapper.bookingToBookingDto(booking);
    }

    @Override
    public BookingDto removeBooking(BookingCreateDto bookingCreateDto) {
        Booking booking = reservationMapper.bookingCreateDtoToBooking(bookingCreateDto);
        ClientBookingDto clientBookingDto = new ClientBookingDto(booking.getUsername());
        clientBookingDto.setIncrement(false);
        clientBookingDto.setBookingId(booking.getId());
        jmsTemplate.convertAndSend(bookingDestination, messageHelper.createTextMessage(clientBookingDto));
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
    public void forwardClientAndBooking(ClientQueueDto clientQueueDto) {
        // FindBookingByUsernameAndID
        Booking booking = bookingRepository.findBookingByUsername(clientQueueDto.getUsername());
        BookingClientDto bookingClientDto = new BookingClientDto();
        bookingClientDto.setArrival(booking.getArrival());
        bookingClientDto.setDeparture(booking.getDeparture());
        bookingClientDto.setCity(booking.getCity());
        bookingClientDto.setHotelName(booking.getHotelName());
        bookingClientDto.setRoomType(booking.getRooms().getType());
        bookingClientDto.setBirthday(clientQueueDto.getBirthday());
        bookingClientDto.setEmail(clientQueueDto.getEmail());
        bookingClientDto.setFirstName(clientQueueDto.getFirstName());
        bookingClientDto.setLastName(clientQueueDto.getLastName());
        bookingClientDto.setUsername(clientQueueDto.getUsername());
        bookingClientDto.setIncrement(clientQueueDto.getIncrement());

        if (clientQueueDto.getIncrement()==false) bookingRepository.delete(booking);
        jmsTemplate.convertAndSend(forwardClientBookingDestination, messageHelper.createTextMessage(bookingClientDto));
    }
}
