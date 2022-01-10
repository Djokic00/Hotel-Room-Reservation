package sk.hoteluserservice.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sk.hoteluserservice.dto.IncrementBookingDto;
import sk.hoteluserservice.listener.helper.MessageHelper;
import sk.hoteluserservice.service.UserService;

import javax.jms.JMSException;
import javax.jms.Message;


@Component
public class BookingListener {

    private MessageHelper messageHelper;
    private UserService userService;

    public BookingListener(MessageHelper messageHelper, UserService userService) {
        this.messageHelper = messageHelper;
        this.userService = userService;
    }

    @JmsListener(destination = "${destination.incrementBooking}", concurrency = "5-10")
    public void incrementNumberOfBooking(Message message) throws JMSException {
        IncrementBookingDto client = messageHelper.getMessage(message, IncrementBookingDto.class);
        userService.incrementReservation(client.getUsername());
    }


}
