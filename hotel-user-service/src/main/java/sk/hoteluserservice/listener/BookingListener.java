package sk.hoteluserservice.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sk.hoteluserservice.dto.ClientBookingDto;
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

    @JmsListener(destination = "${destination.bookingNumber}", concurrency = "5-10")
    public void incrementNumberOfBooking(Message message) throws JMSException {
        ClientBookingDto client = messageHelper.getMessage(message, ClientBookingDto.class);
        System.out.println("user: " + client.toString());
        userService.changeNumberOfReservations(client);
    }

}
