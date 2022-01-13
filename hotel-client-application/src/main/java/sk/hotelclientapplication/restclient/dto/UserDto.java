package sk.hotelclientapplication.restclient.dto;
import java.sql.Date;

public class UserDto{

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private Boolean isBanned;
    private Boolean enabled;
    //private Date birthday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

//    public Date getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(Date bithday) {
//        this.birthday = bithday;
//    }
}
