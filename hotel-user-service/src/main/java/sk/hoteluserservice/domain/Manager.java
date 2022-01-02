package sk.hoteluserservice.domain;

import javax.persistence.*;
import java.sql.Date;


@Entity
public class Manager extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hotelname;
//    private Date hiringDate;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

//    public Date getHiringDate() {
//        return hiringDate;
//    }
//
//    public void setHiringDate(Date hiringDate) {
//        this.hiringDate = hiringDate;
//    }
}
