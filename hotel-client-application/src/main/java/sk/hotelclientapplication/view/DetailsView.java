package sk.hotelclientapplication.view;

import sk.hotelclientapplication.model.UserTableModel;
import sk.hotelclientapplication.restclient.UserServiceRestClient;
import sk.hotelclientapplication.restclient.dto.UserListDto;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DetailsView extends JPanel {

    private UserTableModel userTableModel ;
    private JTable userTable;
    private UserServiceRestClient userServiceRestClient;
    private JButton jButton;

    public DetailsView() throws IllegalAccessException, NoSuchMethodException, IOException {
        super();
        this.setSize(400, 400);

        userTableModel = new UserTableModel();
        userServiceRestClient = new UserServiceRestClient();
        userTable = new JTable(userTableModel);
        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(userTable);
        this.add(scrollPane, BorderLayout.NORTH);

        jButton = new JButton("Ban user");
        this.add(jButton, BorderLayout.CENTER);

        jButton.addActionListener((event) -> {
            System.out.println(userTableModel.getUserListDto().getContent().get(userTable.getSelectedRow()).getId());
        });

        this.setVisible(true);

        UserListDto userListDto = userServiceRestClient.getAllUsers();
        userListDto.getContent().forEach(userDto -> {
            System.out.println(userDto);
            userTableModel.addRow(new Object[]{userDto.getUsername(), userDto.getEmail(), userDto.getId()});
        });


    }



    public UserTableModel getUserTableModel() {
        return userTableModel;
    }

    public void setUserTableModel(UserTableModel userTableModel) {
        this.userTableModel = userTableModel;
    }
}
