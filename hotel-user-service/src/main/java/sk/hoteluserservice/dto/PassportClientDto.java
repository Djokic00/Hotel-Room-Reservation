package sk.hoteluserservice.dto;

import javax.validation.constraints.NotBlank;

public class PassportClientDto {
    @NotBlank
    private String passportNumber;

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
}
