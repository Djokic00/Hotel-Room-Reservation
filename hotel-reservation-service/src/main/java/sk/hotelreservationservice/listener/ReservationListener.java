package sk.hotelreservationservice.listener;

import org.springframework.jms.annotation.JmsListener;
import sk.hotelreservationservice.dto.ClientDto;
import sk.hotelreservationservice.listener.helper.MessageHelper;
import sk.hotelreservationservice.service.ReservationService;

import javax.jms.JMSException;
import javax.jms.Message;

public class ReservationListener {
    private MessageHelper messageHelper;
    private ReservationService reservationService;

    ReservationListener(MessageHelper messageHelper, ReservationService reservationService) {
        this.messageHelper = messageHelper;
        this.reservationService = reservationService;
    }

    @JmsListener(destination = "${destination.findEmail}", concurrency = "5-10")
    public void forwardClientAndBooking(Message message) throws JMSException {
        ClientDto clientDto = messageHelper.getMessage(message, ClientDto.class);
        reservationService.forwardClientAndBooking(clientDto);
    }
}
