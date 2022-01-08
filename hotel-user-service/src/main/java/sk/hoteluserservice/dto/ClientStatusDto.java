package sk.hoteluserservice.dto;

public class ClientStatusDto {
    private String rank;
    private Integer discount;

    public ClientStatusDto() {}

    public ClientStatusDto(Integer discount, String rank) {
        this.discount = discount;
        this.rank = rank;
    }

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
