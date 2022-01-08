package sk.hotelnotificationservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.hotelnotificationservice.dto.NotificationTypeCreateDto;
import sk.hotelnotificationservice.dto.NotificationTypeDto;
import sk.hotelnotificationservice.mapper.NotificationMapper;
import sk.hotelnotificationservice.repository.NotificationTypeRepository;
import sk.hotelnotificationservice.service.NotificationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private NotificationService notificationService;
    private NotificationMapper notificationMapper;
    private NotificationTypeRepository notificationTypeRepository;

    public NotificationController(NotificationService notificationService, NotificationMapper notificationMapper,
                                  NotificationTypeRepository notificationTypeRepository) {
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
        this.notificationTypeRepository = notificationTypeRepository;
    }

    @ApiOperation(value = "Add notification")
    @PostMapping("/notificationType")
    public ResponseEntity<NotificationTypeDto> saveNotificationType(@RequestBody @Valid
                                                                      NotificationTypeCreateDto notificationTypeCreateDto) {
        return new ResponseEntity<>(notificationService.addNotificationType(notificationTypeCreateDto), HttpStatus.CREATED);
    }

}
