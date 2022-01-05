package sk.hoteluserservice.dto;

import org.hibernate.validator.constraints.Length;

public class PasswordClientDto {

    @Length(min = 8, max = 20)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
