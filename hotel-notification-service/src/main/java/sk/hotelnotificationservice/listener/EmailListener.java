package sk.hotelnotificationservice.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sk.hotelnotificationservice.dto.UserDto;
import sk.hotelnotificationservice.listener.helper.MessageHelper;
import sk.hotelnotificationservice.service.EmailService;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class EmailListener {

    private MessageHelper messageHelper;
    private EmailService emailService;

    public EmailListener(MessageHelper messageHelper, EmailService emailService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
    }

    @JmsListener(destination = "${destination.registerClient}", concurrency = "5-10")
    public void registerClient(Message message) throws JMSException {
        UserDto userDto = messageHelper.getMessage(message, UserDto.class);
        emailService.sendSimpleMessage("estojanovic141@gmail.com", "subject", userDto.toString());
    }
}