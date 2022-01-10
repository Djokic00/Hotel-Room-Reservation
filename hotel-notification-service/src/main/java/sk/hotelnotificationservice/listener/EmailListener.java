package sk.hotelnotificationservice.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sk.hotelnotificationservice.dto.BookingClientDto;
import sk.hotelnotificationservice.dto.ClientDto;
import sk.hotelnotificationservice.listener.helper.MessageHelper;
import sk.hotelnotificationservice.mapper.NotificationMapper;
import sk.hotelnotificationservice.service.EmailService;
import sk.hotelnotificationservice.service.NotificationService;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class EmailListener {

    private MessageHelper messageHelper;
    private EmailService emailService;
    private NotificationService notificationService;

    public EmailListener(MessageHelper messageHelper, EmailService emailService,
                         NotificationService notificationService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
        this.notificationService = notificationService;
    }

    @JmsListener(destination = "${destination.registerClient}", concurrency = "5-10")
    public void registerClient(Message message) throws JMSException {
        ClientDto clientDto = messageHelper.getMessage(message, ClientDto.class);
        notificationService.sendMail(clientDto,"register");
    }
    @JmsListener(destination = "${destination.forwardClientBooking}", concurrency = "5-10")
    public void reservationNotification(Message message) throws JMSException {
        BookingClientDto bookingClientDto = messageHelper.getMessage(message, BookingClientDto.class);
        if (bookingClientDto.getIncrement())
        notificationService.sendReservationMail(bookingClientDto,"reservation");
        else notificationService.sendReservationMail(bookingClientDto,"cancel reservation");
    }


}