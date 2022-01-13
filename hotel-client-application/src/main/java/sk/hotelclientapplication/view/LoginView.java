package sk.hotelclientapplication.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.swing.interop.SwingInterOpUtils;
import sk.hotelclientapplication.ClientApplication;
import sk.hotelclientapplication.restclient.UserServiceRestClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginView extends JPanel {

    private JPanel inputPanel;

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameInput;
    private JPasswordField passwordInput;

    private JButton loginButton;
    private JButton registerClientButton;
    private JButton registerManagerButton;

    private UserServiceRestClient userServiceRestClient = new UserServiceRestClient();

    private ObjectMapper objectMapper = new ObjectMapper();

    public LoginView() {

        super();
        this.setSize(400, 400);

        this.setLayout(new GridLayout(4,1));

        initInputPanel();

        loginButton = new JButton("Login");


        this.add(loginButton);

        loginButton.addActionListener((event) -> {

            try {
                String token = userServiceRestClient
                        .login(usernameInput.getText(), String.valueOf(passwordInput.getPassword()));
                this.setVisible(false);
                ClientApplication.getInstance().setToken(token);
                System.out.println(token);
                ClientView clientView = new ClientView();
                clientView.setVisible(true);

//                ManagerView managerView = new ManagerView();
//                managerView.setVisible(true);
                ClientApplication.getInstance().hide();
                //ClientApplication.getInstance().getMoviesView().init();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });



        registerClientButton = new JButton("Register as Client");
        this.add(registerClientButton);

        registerManagerButton = new JButton("Register as Manager");
        this.add(registerManagerButton);



        registerClientButton.addActionListener((event) -> {
            System.out.println("registrujem klijenta");

            System.out.println("setovao sam log false");
            RegisterClientView registerClientView= new RegisterClientView();
            registerClientView.setVisible(true);
            ClientApplication.getInstance().hide();
           // System.out.println(  ClientApplication.getInstance().getProba());


        });

        registerManagerButton.addActionListener((event) -> {

            System.out.println("regisrtujem menadzera");

            RegisterManagerView registerManagerView=new RegisterManagerView();
            registerManagerView.setVisible(true);
            ClientApplication.getInstance().hide();
        });

    }

    private void initInputPanel() {

        inputPanel = new JPanel();

        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");

        usernameInput = new JTextField(20);
        passwordInput = new JPasswordField(20);

        inputPanel.add(usernameLabel);
        inputPanel.add(usernameInput);

        inputPanel.add(passwordLabel);
        inputPanel.add(passwordInput);

        this.add(inputPanel, BorderLayout.CENTER);
    }
}
