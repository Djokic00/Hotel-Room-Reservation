package sk.hotelreservationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class CommentDto {

    private Long id;
    private String text;
    private String username;
    private Integer hotelRating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getHotelRating() {
        return hotelRating;
    }

    public void setHotelRating(Integer hotelRating) {
        this.hotelRating = hotelRating;
    }


}
