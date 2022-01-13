package sk.hotelclientapplication.model;

import sk.hotelclientapplication.restclient.dto.UserDto;
import sk.hotelclientapplication.restclient.dto.UserListDto;

import javax.swing.table.DefaultTableModel;

public class UserTableModel extends DefaultTableModel {


    public UserTableModel() throws IllegalAccessException, NoSuchMethodException {
        super(new String[]{"Username", "email"}, 0);
    }

    private UserListDto userListDto = new UserListDto();

    @Override
    public void addRow(Object[] row) {
        super.addRow(row);
        UserDto dto = new UserDto();
        dto.setUsername(String.valueOf(row[0]));
        dto.setEmail(String.valueOf(row[1]));
        dto.setId(Long.valueOf(String.valueOf(row[2])));
        userListDto.getContent().add(dto);
    }

    public UserListDto getUserListDto() {
        return userListDto;
    }

    public void setUserListDto(UserListDto userListDto) {
        this.userListDto = userListDto;
    }
}
