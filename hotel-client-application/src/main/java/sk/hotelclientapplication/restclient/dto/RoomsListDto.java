package sk.hotelclientapplication.restclient.dto;

import java.util.ArrayList;
import java.util.List;

public class RoomsListDto {

    private List<RoomsDto> content = new ArrayList<>();

    public RoomsListDto(List<RoomsDto> content) {
        this.content = content;
    }

    public RoomsListDto() {
    }

    public List<RoomsDto> getContent() {
        return content;
    }

    public void setContent(List<RoomsDto> content) {
        this.content = content;
    }
}
