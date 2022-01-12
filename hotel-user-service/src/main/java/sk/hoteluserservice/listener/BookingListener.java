package sk.hoteluserservice.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sk.hoteluserservice.dto.ClientCreateDto;
import sk.hoteluserservice.dto.ClientQueueDto;
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

//    @JmsListener(destination = "${destination.bookingNumber}", concurrency = "5-10")
//    public void incrementNumberOfBooking(Message message) throws JMSException {
//        ClientBookingDto client = messageHelper.getMessage(message, ClientBookingDto.class);
//
//        userService.changeNumberOfReservations(client);
//    }

    @JmsListener(destination = "${destination.bookingNumber}", concurrency = "5-10")
    public void incrementNumberOfBooking(Message message) throws JMSException {
        ClientQueueDto clientQueueDto = messageHelper.getMessage(message, ClientQueueDto.class);
        System.out.println(clientQueueDto.toString());
        userService.changeNumberOfReservations(clientQueueDto);
    }


}
