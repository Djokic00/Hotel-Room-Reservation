package sk.hotelclientapplication.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import sk.hotelclientapplication.ClientApplication;
import sk.hotelclientapplication.restclient.UserServiceRestClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RegisterClientView extends JFrame {

    private GridLayout gridLayout;

    private JLabel email;
    private JLabel firstname;
    private JLabel lastname;
    private JLabel username;
    private JLabel password;
    private JLabel birthday;
    private JLabel contact;
    private JLabel passportnumber;
    private JTextField emailInput;
    private JPasswordField passwordInput;
    private JTextField firstnameInput;
    private JTextField lastnameInput;
    private JTextField usernameInput;
    private JTextField birthdayInput;
    private JTextField contactInput;
    private JTextField passportInput;


    private JButton register;

    private UserServiceRestClient userServiceRestClient = new UserServiceRestClient();


    public RegisterClientView() {

        super();
        this.setSize(400, 600);

        this.setLayout(new GridLayout(9,2));

        email = new JLabel("Email: ");
        firstname = new JLabel("First name: ");
        lastname = new JLabel("Last name: ");
        username = new JLabel("Username: ");
        password = new JLabel("Password: ");
        birthday = new JLabel("Birthday: ");
        contact = new JLabel("Contact: ");
        passportnumber = new JLabel("Passport: ");


        emailInput = new JTextField(20);
        firstnameInput = new JTextField(20);
        lastnameInput = new JTextField(20);
        usernameInput = new JTextField(20);
        passwordInput = new JPasswordField(20);
        birthdayInput = new JTextField(20);
        contactInput = new JTextField(20);
        passportInput = new JTextField(20);


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
        this.add(passportnumber);
        this.add(passportInput);

        register = new JButton("Register");

        this.add(register);

        register.addActionListener((event) -> {

            System.out.println("registrovao sam");
            try {
                userServiceRestClient
                        .registerClient(emailInput.getText(),firstnameInput.getText(),lastnameInput.getText(),usernameInput.getText(), String.valueOf(passwordInput.getPassword()), birthdayInput.getText(),contactInput.getText(),passportInput.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        System.out.println("napravio sam novi register");

        this.setVisible(true);
    }

    public void init() throws IOException {
        this.setVisible(true);
        System.out.println("visible");


    }
}
