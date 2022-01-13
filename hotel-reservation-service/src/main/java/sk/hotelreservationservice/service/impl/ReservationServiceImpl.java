package sk.hotelreservationservice.service.impl;

import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import sk.hotelreservationservice.domain.Booking;
import sk.hotelreservationservice.domain.Comment;
import sk.hotelreservationservice.domain.Hotel;
import sk.hotelreservationservice.domain.Rooms;
import sk.hotelreservationservice.dto.*;
import sk.hotelreservationservice.exception.NotFoundException;
import sk.hotelreservationservice.listener.helper.MessageHelper;
import sk.hotelreservationservice.mapper.ReservationMapper;
import sk.hotelreservationservice.repository.BookingRepository;
import sk.hotelreservationservice.repository.CommentRepository;
import sk.hotelreservationservice.repository.HotelRepository;
import sk.hotelreservationservice.repository.RoomsRepository;
import sk.hotelreservationservice.service.ReservationService;
import sk.hotelreservationservice.userservice.UserServiceClientConfiguration;
import sk.hotelreservationservice.userservice.dto.ClientQueueDto;
import sk.hotelreservationservice.userservice.dto.ClientStatusDto;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

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
    private CommentRepository commentRepository;
    private Retry userServiceRetry;

    public ReservationServiceImpl(ReservationMapper reservationMapper,
                                  HotelRepository hotelRepository, RoomsRepository roomsRepository,
                                  BookingRepository bookingRepository,
                                  JmsTemplate jmsTemplate, @Value("${destination.bookingNumber}") String bookingDestination,
                                  @Value("${destination.forwardClientBooking}") String forwardClientBookingDestination,
                                  MessageHelper messageHelper,
                                  RestTemplate userServiceClientConfiguration, CommentRepository commentRepository,
                                  Retry userServiceRetry) {
        this.reservationMapper = reservationMapper;
        this.hotelRepository = hotelRepository;
        this.roomsRepository = roomsRepository;
        this.bookingRepository = bookingRepository;
        this.jmsTemplate = jmsTemplate;
        this.bookingDestination = bookingDestination;
        this.forwardClientBookingDestination = forwardClientBookingDestination;
        this.messageHelper = messageHelper;
        this.userServiceRestTemplate = userServiceClientConfiguration;
        this.commentRepository = commentRepository;
        this.userServiceRetry = userServiceRetry;
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

        ClientStatusDto discountDto = Retry.decorateSupplier(userServiceRetry, () -> getDiscount(bookingCreateDto.getUserId())).get();

//        mora da pronadje cenu za taj tip sobe i za taj hotel i za taj grad
//        broj_nocenja*cena*(100-popust)/100
        LocalDate start = LocalDate.parse(booking.getArrival().toString());
        LocalDate end = LocalDate.parse(booking.getDeparture().toString());
        long diff = DAYS.between(start, end);

        String roomPrice = roomsRepository.findPriceByHotelCityAndRoomType(booking.getHotelName(),booking.getCity(),booking.getRooms().getType());
        Double newPrice = diff * Double.parseDouble(roomPrice) * (100 - discountDto.getDiscount()) / 100;
        booking.setPrice(String.valueOf(newPrice));

        bookingRepository.save(booking);

        Booking lastBooking = bookingRepository.findLastBookingById(booking.getUserId());
        ClientQueueDto clientQueueDto = new ClientQueueDto();
        clientQueueDto.setIncrement(true);
        clientQueueDto.setUserId(lastBooking.getUserId());
        clientQueueDto.setBookingId(lastBooking.getId());
        clientQueueDto.setHotelName(lastBooking.getHotelName());
        clientQueueDto.setCity(lastBooking.getCity());
        jmsTemplate.convertAndSend(bookingDestination, messageHelper.createTextMessage(clientQueueDto));
        return reservationMapper.bookingToBookingDto(booking);
    }

    private ClientStatusDto getDiscount(Long id) {
        System.out.println("Getting user with id: " + id);
        try {
            return userServiceRestTemplate.exchange("/user/" +
                    id + "/discount", HttpMethod.GET, null, ClientStatusDto.class).getBody();
        } catch (HttpClientErrorException e) {
                throw new NotFoundException(String.format("User with that id is: %d not found.", id));
        } catch (Exception e) {
            throw new RuntimeException("Internal server error");
        }
    }

    @Override
    public BookingDto removeBooking(BookingCreateDto bookingCreateDto, Long bookingId) {
        Booking booking = reservationMapper.bookingCreateDtoToBooking(bookingCreateDto);
//        ClientBookingDto clientBookingDto = new ClientBookingDto(booking.getUsername());
//        clientBookingDto.setIncrement(false);
//        clientBookingDto.setBookingId(bookingId);
//        jmsTemplate.convertAndSend(bookingDestination, messageHelper.createTextMessage(clientBookingDto));
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
        Booking booking = bookingRepository.findBookingById(clientQueueDto.getBookingId());
        BookingClientDto bookingClientDto = new BookingClientDto();
        bookingClientDto.setUserId(booking.getUserId());
        bookingClientDto.setArrival(booking.getArrival());
        bookingClientDto.setDeparture(booking.getDeparture());
        bookingClientDto.setCity(booking.getCity());
        bookingClientDto.setHotelName(booking.getHotelName());
        bookingClientDto.setRoomType(booking.getRooms().getType());
        bookingClientDto.setBirthday(clientQueueDto.getBirthday());
        bookingClientDto.setEmail(clientQueueDto.getEmail());
        bookingClientDto.setFirstName(clientQueueDto.getFirstName());
        bookingClientDto.setLastName(clientQueueDto.getLastName());
        bookingClientDto.setIncrement(clientQueueDto.getIncrement());
        bookingClientDto.setManagerEmail(clientQueueDto.getManagerEmail());

        if (!clientQueueDto.getIncrement()) bookingRepository.delete(booking);
        jmsTemplate.convertAndSend(forwardClientBookingDestination, messageHelper.createTextMessage(bookingClientDto));
    }

    @Override
    public Page<CommentDto> findAllByHotelId(Long hotelId, Pageable pageable) {
        return commentRepository.findAllByHotel_Id(hotelId, pageable)
                .map(reservationMapper::commentToCommentDto);
    }

    @Override
    public CommentDto addCommentOnHotel(Long hotelId, CommentCreateDto commentCreateDto) {
        Hotel hotel = hotelRepository.findHotelById(hotelId);
        Comment comment = commentRepository
                .save(reservationMapper.commentCreateDtoToComment(commentCreateDto, hotel));
        return reservationMapper.commentToCommentDto(comment);
    }

}
