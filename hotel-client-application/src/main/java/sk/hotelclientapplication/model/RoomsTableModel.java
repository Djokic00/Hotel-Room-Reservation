package sk.hotelclientapplication.model;

import sk.hotelclientapplication.restclient.dto.RoomsDto;
import sk.hotelclientapplication.restclient.dto.RoomsListDto;
import sk.hotelclientapplication.restclient.dto.UserDto;
import sk.hotelclientapplication.restclient.dto.UserListDto;

import javax.swing.table.DefaultTableModel;

public class RoomsTableModel extends DefaultTableModel {


    public RoomsTableModel() throws IllegalAccessException, NoSuchMethodException {
        super(new String[]{"hotelName", "type"}, 0);
    }

    private RoomsListDto userListDto = new RoomsListDto();

    @Override
    public void addRow(Object[] row) {
        super.addRow(row);
        RoomsDto dto = new RoomsDto();
        dto.setHotelName(String.valueOf(row[0]));
        dto.setType(String.valueOf(row[1]));
       // dto.(Long.valueOf(String.valueOf(row[2])));
        userListDto.getContent().add(dto);
    }

    public RoomsListDto getUserListDto() {
        return userListDto;
    }

    public void setUserListDto(RoomsListDto userListDto) {
        this.userListDto = userListDto;
    }
}
