package sk.hoteluserservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DiscountCreateDto {
    @NotBlank
    private String rank;

    @NotNull
    private Integer discount;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }


    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
