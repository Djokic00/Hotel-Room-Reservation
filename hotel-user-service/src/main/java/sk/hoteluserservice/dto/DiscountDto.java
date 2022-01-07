package sk.hoteluserservice.dto;

public class DiscountDto {
    private String rank;
    private Integer discount;

    public Integer getDiscount() {
        return discount;
    }

    public DiscountDto() {}

    public DiscountDto(Integer discount, String rank) {
        this.discount = discount;
        this.rank = rank;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
}
