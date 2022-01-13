package sk.hotelreservationservice.mapper;

import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Component;
import sk.hotelreservationservice.domain.Booking;
import sk.hotelreservationservice.domain.Comment;
import sk.hotelreservationservice.domain.Hotel;
import sk.hotelreservationservice.domain.Rooms;
import sk.hotelreservationservice.dto.*;
import sk.hotelreservationservice.repository.HotelRepository;
import sk.hotelreservationservice.repository.RoomsRepository;

@Component
public class ReservationMapper {

    private HotelRepository hotelRepository;
    private RoomsRepository roomsRepository;

    public ReservationMapper(HotelRepository hotelRepository, RoomsRepository roomsRepository) {
        this.hotelRepository = hotelRepository;
        this.roomsRepository = roomsRepository;
    }

    public HotelDto hotelToHotelDto(Hotel hotel) {
        HotelDto hotelDto = new HotelDto();
        hotelDto.setHotelName(hotel.getHotelName());
        hotelDto.setCity(hotel.getCity());
        hotelDto.setDescription(hotel.getDescription());
        hotelDto.setNumberOfRooms(hotel.getNumberOfRooms());
        return hotelDto;
    }

    public Hotel hotelCreateDtoToHotel(HotelCreateDto hotelCreateDto) {
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelCreateDto.getHotelName());
        hotel.setCity(hotelCreateDto.getCity());
        hotel.setDescription(hotelCreateDto.getDescription());
        hotel.setNumberOfRooms(hotelCreateDto.getNumberOfRooms());
        return hotel;
    }

    public RoomsDto roomsToRoomsDto(Rooms rooms) {
        RoomsDto roomsDto = new RoomsDto();
        roomsDto.setHotelName(rooms.getHotel().getHotelName());
        roomsDto.setFirstNo(rooms.getFirstRoomNumber());
        roomsDto.setLastNo(rooms.getLastRoomNumber());
        roomsDto.setType(rooms.getType());
        roomsDto.setPrice(rooms.getPrice());
       // roomsDto.setAvailableRooms(rooms.getAvailableRooms());
        return roomsDto;
    }

    public Rooms roomsCreateDtoToRooms(RoomsCreateDto roomsCreateDto) {
        Rooms rooms = new Rooms();
        rooms.setFirstRoomNumber(roomsCreateDto.getFirstNo());
        rooms.setLastRoomNumber(roomsCreateDto.getLastNo());
        rooms.setType(roomsCreateDto.getType());
        rooms.setPrice(roomsCreateDto.getPrice());
        rooms.setHotel(hotelRepository.findHotelByHotelName(roomsCreateDto.getHotelName()));
     //   rooms.setAvailableRooms(roomsCreateDto.getLastNo()-roomsCreateDto.getFirstNo());
        return rooms;
    }

    public BookingDto bookingToBookingDto(Booking booking){
        BookingDto bookingDto = new BookingDto();
        bookingDto.setArrival(booking.getArrival());
        bookingDto.setDeparture(booking.getDeparture());
        bookingDto.setRoomType(booking.getRooms().getType());
        bookingDto.setHotelName(booking.getHotelName());
        bookingDto.setCity(booking.getCity());
        return bookingDto;
    }

    public Booking bookingCreateDtoToBooking(BookingCreateDto bookingCreateDto){
        Booking booking = new Booking();
        booking.setArrival(bookingCreateDto.getArrival());
        booking.setDeparture(bookingCreateDto.getDeparture());
        booking.setRooms(roomsRepository.findRoomsByType(bookingCreateDto.getRoomType()));
        booking.setHotelName(bookingCreateDto.getHotelName());
        booking.setCity(bookingCreateDto.getCity());
        booking.setUserId(bookingCreateDto.getUserId());
        return booking;
    }

    public CommentDto commentToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setUsername(comment.getUsername());
        commentDto.setHotelRating(comment.getHotelRating());
        commentDto.setText(comment.getText());

        return commentDto;
    }



    public Comment commentCreateDtoToComment(CommentCreateDto commentCreateDto, Hotel hotel) {
        Comment comment = new Comment();
        comment.setText(commentCreateDto.getText());
        comment.setUsername(commentCreateDto.getUsername());
        comment.setHotelRating(commentCreateDto.getHotelRating());
        comment.setHotel(hotel);
        return comment;
    }


}
