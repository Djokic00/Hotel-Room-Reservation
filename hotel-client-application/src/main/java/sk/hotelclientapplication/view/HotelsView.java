package sk.hotelclientapplication.view;

import sk.hotelclientapplication.restclient.ReservationServiceRestClient;
import sk.hotelclientapplication.restclient.UserServiceRestClient;
import sk.hotelclientapplication.restclient.dto.ClientCreateDto;
import sk.hotelclientapplication.restclient.dto.HotelCreateDto;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Date;

public class HotelsView extends JPanel {

    private GridLayout gridLayout;

    private JLabel hotelname;
    private JLabel description;
    private JLabel numberofrooms;
    private JLabel city;

    private JTextField hotelnameInput;
    private JTextField descriptionInput;
    private JTextField numberofRoomsInput;
    private JTextField cityInput;



    private JButton register;

    private UserServiceRestClient userServiceRestClient = new UserServiceRestClient();
    private ReservationServiceRestClient reservationServiceRestClient = new ReservationServiceRestClient();

    public HotelsView() {
        super();
        this.setSize(400, 600);

        this.setLayout(new GridLayout(9, 2));

        hotelname = new JLabel("Hotel name: ");
        city = new JLabel("City: ");
        numberofrooms = new JLabel("Number of rooms: ");
        description = new JLabel("Opis: ");


        hotelnameInput = new JTextField(20);
        cityInput = new JTextField(20);
        numberofRoomsInput = new JTextField(20);
        descriptionInput = new JTextField(20);


        this.add(hotelname);
        this.add(hotelnameInput);
        this.add(city);
        this.add(cityInput);
        this.add(numberofrooms);
        this.add(numberofRoomsInput);
        this.add(description);
        this.add(descriptionInput);


        register = new JButton("add Hotel");

        this.add(register);

        register.addActionListener((event) -> {

            try {
                HotelCreateDto hotelCreateDto = new HotelCreateDto();

                hotelCreateDto.setHotelName(hotelnameInput.getText());
                hotelCreateDto.setCity(cityInput.getText());
                hotelCreateDto.setDescription(descriptionInput.getText());
                hotelCreateDto.setNumberOfRooms(Integer.parseInt(numberofRoomsInput.getText()));
               reservationServiceRestClient.saveHotel(hotelCreateDto);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        System.out.println("napravio sam novi register");

        this.setVisible(true);


    }
}