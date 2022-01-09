package sk.hotelreservationservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RoomsCreateDto {

    @NotBlank
    private String type;
    @NotBlank
    private String price;
    @NotNull
    private Integer firstNo;
    @NotNull
    private Integer lastNo;
    @NotBlank
    private String hotelname;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getFirstNo() {
        return firstNo;
    }

    public void setFirstNo(Integer firstNo) {
        this.firstNo = firstNo;
    }

    public Integer getLastNo() {
        return lastNo;
    }

    public void setLastNo(Integer lastNo) {
        this.lastNo = lastNo;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }
}
