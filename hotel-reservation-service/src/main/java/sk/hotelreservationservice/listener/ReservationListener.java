package sk.hotelreservationservice.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sk.hotelreservationservice.dto.ClientDto;
import sk.hotelreservationservice.listener.helper.MessageHelper;
import sk.hotelreservationservice.service.ReservationService;
import sk.hotelreservationservice.userservice.dto.ClientQueueDto;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class ReservationListener {
    private MessageHelper messageHelper;
    private ReservationService reservationService;

    ReservationListener(MessageHelper messageHelper, ReservationService reservationService) {
        this.messageHelper = messageHelper;
        this.reservationService = reservationService;
    }

    @JmsListener(destination = "${destination.findEmail}", concurrency = "5-10")
    public void forwardClientAndBooking(Message message) throws JMSException {
        ClientQueueDto clientQueueDto = messageHelper.getMessage(message, ClientQueueDto.class);
        System.out.println(clientQueueDto.toString());
        reservationService.forwardClientAndBooking(clientQueueDto);
    }
}
