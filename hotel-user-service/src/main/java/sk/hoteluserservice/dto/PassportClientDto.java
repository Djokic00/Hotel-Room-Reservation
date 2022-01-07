package sk.hoteluserservice.dto;

import javax.validation.constraints.NotNull;

public class PassportClientDto {
    @NotNull
    private Integer passportNumber;

    public Integer getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(Integer passportNumber) {
        this.passportNumber = passportNumber;
    }
}
