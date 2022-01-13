package sk.hotelclientapplication.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import sk.hotelclientapplication.ClientApplication;
import sk.hotelclientapplication.model.RoomsTableModel;
import sk.hotelclientapplication.model.User;
import sk.hotelclientapplication.model.UserTableModel;
import sk.hotelclientapplication.restclient.ReservationServiceRestClient;
import sk.hotelclientapplication.restclient.UserServiceRestClient;
import sk.hotelclientapplication.restclient.dto.BookingCreateDto;
import sk.hotelclientapplication.restclient.dto.HotelCreateDto;
import sk.hotelclientapplication.restclient.dto.RoomsListDto;
import sk.hotelclientapplication.restclient.dto.UserListDto;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Base64;

public class MakeReservationView  extends JPanel {

    private GridLayout gridLayout;

    private JLabel arrival;
    private JLabel departure;
    private JLabel roomType;
    private JLabel hotelname;
    private JLabel city;


    private JTextField hotelnameInput;
    private JTextField arrivalInput;
    private JTextField departureInput;
    private JTextField roomTypeInput;
    private JTextField cityInput;
    private RoomsListDto roomsListDto;
    private RoomsTableModel roomsTableModel;

    private JTable roomsTable;



    private JButton book;
    ObjectMapper objectMapper = new ObjectMapper();

    //private UserServiceRestClient userServiceRestClient = new UserServiceRestClient();
    private ReservationServiceRestClient reservationServiceRestClient = new ReservationServiceRestClient();

    public MakeReservationView() {
        super();
        this.setSize(400, 600);

        this.setLayout(new GridLayout(9, 2));

        hotelname = new JLabel("Hotel name: ");
        city = new JLabel("City: ");
        roomType = new JLabel("Room type: ");
        arrival = new JLabel("Arrival: ");
        departure = new JLabel("Departure: ");


        hotelnameInput = new JTextField(20);
        cityInput = new JTextField(20);
        arrivalInput = new JTextField(20);
        departureInput = new JTextField(20);
        roomTypeInput = new JTextField(20);


        this.add(hotelname);
        this.add(hotelnameInput);
        this.add(city);
        this.add(cityInput);
        this.add(roomType);
        this.add(roomTypeInput);
        this.add(arrival);
        this.add(arrivalInput);
        this.add(departure);
        this.add(departureInput);

        book = new JButton("book");

        this.add(book);

        book.addActionListener((event) -> {

            try {
                BookingCreateDto bookingCreateDto = new BookingCreateDto();
                bookingCreateDto.setArrival(arrivalInput.getText());
                bookingCreateDto.setDeparture(departureInput.getText());
                bookingCreateDto.setHotelName(hotelnameInput.getText());
                bookingCreateDto.setCity(cityInput.getText());
                bookingCreateDto.setRoomType(roomTypeInput.getText());
//
//                String tokenPayloadEncoded= ClientApplication.getInstance().getToken().split("\\.")[1];
//                String json = new String(Base64.getDecoder().decode(tokenPayloadEncoded));
//                Long userId = objectMapper.readValue(json, User.class).getId();
                Long userId = Long.valueOf(2);


                bookingCreateDto.setUserId(userId);
                reservationServiceRestClient.saveBooking(bookingCreateDto);



                JPanel panel=new JPanel();
                try {
                    roomsTableModel = new RoomsTableModel();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                roomsTable = new JTable(roomsTableModel);
                panel.setLayout(new BorderLayout());
                JScrollPane scrollPane = new JScrollPane(roomsTable);
                panel.add(scrollPane, BorderLayout.NORTH);

                try {
                    roomsListDto = reservationServiceRestClient.getAllRooms(bookingCreateDto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                roomsListDto.getContent().forEach(roomsDto -> {
                    System.out.println(roomsDto);
                    roomsTableModel.addRow(new Object[]{roomsDto.getHotelName(),roomType});
                });

                System.out.println("napravio sam novi register");
                this.add(panel);
                panel.setVisible(true);




            } catch (IOException e) {
                e.printStackTrace();
            }

        });





    }
}
