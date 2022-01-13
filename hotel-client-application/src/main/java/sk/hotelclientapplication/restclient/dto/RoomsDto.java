package sk.hotelclientapplication.restclient.dto;

public class RoomsDto {

    private String type;
    private String price;
    private Integer firstNo;
    private Integer lastNo;
    private String hotelName;
    // private Integer availableRooms;

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

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

//    public Integer getAvailableRooms() {
//        return availableRooms;
//    }
//
//    public void setAvailableRooms(Integer availableRooms) {
//        this.availableRooms = availableRooms;
//    }
}

