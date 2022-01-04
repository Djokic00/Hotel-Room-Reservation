package sk.hoteluserservice.domain;

import javax.persistence.*;

@Entity
public class Client extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer passportNumber;
    private Integer numberOfReservations;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(Integer passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Integer getNumberOfReservations() {
        return numberOfReservations;
    }

    public void setNumberOfReservations(Integer numberOfReservations) {
        this.numberOfReservations = numberOfReservations;
    }
}
