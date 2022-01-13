package sk.hotelclientapplication.restclient.dto;

import java.util.ArrayList;
import java.util.List;

public class UserListDto {

    private List<UserDto> content = new ArrayList<>();

    public UserListDto(List<UserDto> content) {
        this.content = content;
    }

    public UserListDto() {
    }

    public List<UserDto> getContent() {
        return content;
    }

    public void setContent(List<UserDto> content) {
        this.content = content;
    }
}
