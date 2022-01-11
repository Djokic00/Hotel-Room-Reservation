package sk.hotelreservationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class CommentCreateDto{

    @NotEmpty(message = "Text cant be empty")
    private String text;
    @NotEmpty(message = "Username cant be empty")
    private String username;
    @Min(value = 1)
    @Max(value = 5)
    @JsonProperty("hotel_rating")
    private Integer hotelRating;

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
