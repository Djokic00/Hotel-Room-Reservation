package sk.hotelreservationservice.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.hotelreservationservice.dto.*;
import sk.hotelreservationservice.service.ReservationService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println(hotelCreateDto.toString());
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

    @ApiOperation(value = "Remove booking")
    @DeleteMapping("/deleteBooking/{id}")
    public ResponseEntity<BookingDto> removeBooking(@PathVariable("id") Long id,@RequestBody @Valid
                                                          BookingCreateDto bookingCreateDto) {
        return new ResponseEntity<>(reservationService.removeBooking(bookingCreateDto, id), HttpStatus.CREATED);
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

    @ApiOperation(value = "Find comments")
    @GetMapping("/comment/findAll")
    public ResponseEntity<Page<CommentDto>> findAll(@PathVariable("id") Long id, @ApiIgnore Pageable pageable) {
        return new ResponseEntity<>(reservationService.findAllByHotelId(id, pageable), HttpStatus.OK);
    }
    @ApiOperation(value = "Add comment")
    @PostMapping("/comment/add")
    public ResponseEntity<CommentDto> add(@PathVariable("id") Long id, @RequestBody @Valid CommentCreateDto commentCreateDto) {
        return new ResponseEntity<>(reservationService.addCommentOnHotel(id, commentCreateDto), HttpStatus.OK);
    }


    @ApiOperation(value = "Get all available rooms")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping("/allrooms")
    //@CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_CLIENT"})
    public ResponseEntity<ArrayList> getAllRooms(BookingCreateDto bookingCreateDto,
                                            @ApiIgnore Pageable pageable) {
        Page<RoomsDto> page = reservationService.findAll(pageable);

        ArrayList<RoomsDto> list = new ArrayList<>();

        page.forEach(roomsDto -> {
            if (reservationService.availableRooms(bookingCreateDto)>0)
                list.add(roomsDto);
        } );

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
