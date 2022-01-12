package sk.hotelclientapplication.view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RegisterManagerView extends JFrame {

    private GridLayout gridLayout;

    private JLabel email;
    private JLabel firstname;
    private JLabel lastname;
    private JLabel username;
    private JLabel password;
    private JLabel birthday;
    private JLabel contact;
    private JLabel hotelname;
    private JLabel hiringdate;
    private JLabel city;
    private JTextField emailInput;
    private JPasswordField passwordInput;
    private JTextField firstnameInput;
    private JTextField lastnameInput;
    private JTextField usernameInput;
    private JTextField birthdayInput;
    private JTextField contactInput;
    private JTextField hotelnameInput;
    private JTextField hiringdateInput;
    private JTextField cityInput;


    private JButton register;


    public RegisterManagerView() {

        super();
        this.setSize(400, 600);

        this.setLayout(new GridLayout(11,2));

        email = new JLabel("Email: ");
        firstname = new JLabel("First name: ");
        lastname = new JLabel("Last name: ");
        username = new JLabel("Username: ");
        password = new JLabel("Password: ");
        birthday = new JLabel("Birthday: ");
        contact = new JLabel("Password: ");
        hotelname = new JLabel("Hotel name: ");
        city = new JLabel("City");
        hiringdate = new JLabel("Hiring date: ");


        emailInput = new JTextField(20);
        firstnameInput = new JTextField(20);
        lastnameInput = new JTextField(20);
        usernameInput = new JTextField(20);
        passwordInput = new JPasswordField(20);
        birthdayInput = new JTextField(20);
        contactInput = new JTextField(20);
        hotelnameInput = new JTextField(20);
        cityInput = new JTextField(20);
        hiringdateInput = new JTextField(20);


        this.add(email);
        this.add(emailInput);
        this.add(firstname);
        this.add(firstnameInput);
        this.add(lastname);
        this.add(lastnameInput);
        this.add(username);
        this.add(usernameInput);
        this.add(password);
        this.add(passwordInput);
        this.add(birthday);
        this.add(birthdayInput);
        this.add(contact);
        this.add(contactInput);
        this.add(hotelname);
        this.add(hotelnameInput);
        this.add(city);
        this.add(cityInput);
        this.add(hiringdate);
        this.add(hiringdateInput);

        register = new JButton("Register");

        this.add(register);

        register.addActionListener((event) -> {

            System.out.println("registrovao sam menadzera");
        });



        this.setVisible(true);
    }

    public void init() throws IOException {
        this.setVisible(true);
        System.out.println("visible");


    }
}