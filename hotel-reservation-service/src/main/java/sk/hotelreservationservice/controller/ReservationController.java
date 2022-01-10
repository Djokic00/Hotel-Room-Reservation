package sk.hotelreservationservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.hotelreservationservice.dto.*;
import sk.hotelreservationservice.service.ReservationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ApiOperation(value = "Add hotel")
    @PostMapping("/hotel")
    public ResponseEntity<HotelDto> saveHotel(@RequestBody @Valid
                                                                 HotelCreateDto hotelCreateDto) {
        return new ResponseEntity<>(reservationService.addHotel(hotelCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Add rooms")
    @PostMapping("/rooms")
    public ResponseEntity<RoomsDto> saveHotel(@RequestBody @Valid
                                                      RoomsCreateDto roomsCreateDto) {
        return new ResponseEntity<>(reservationService.addRooms(roomsCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Add booking")
    @PostMapping("/booking")
    public ResponseEntity<BookingDto> saveBooking(@RequestBody @Valid
                                                        BookingCreateDto bookingCreateDto) {

        return new ResponseEntity<>(reservationService.addBooking(bookingCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Number of unavailable rooms")
    @PostMapping("/booking/unavailable")
    public ResponseEntity<Integer> unavailable(@RequestBody @Valid
                                                          BookingCreateDto bookingCreateDto) {
        return new ResponseEntity<>(reservationService.unavailableRooms(bookingCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Number of available rooms")
    @PostMapping("/booking/available")
    public ResponseEntity<Integer> availableRooms(@RequestBody @Valid
                                                       BookingCreateDto bookingCreateDto) {
        return new ResponseEntity<>(reservationService.availableRooms(bookingCreateDto), HttpStatus.CREATED);
    }



}
