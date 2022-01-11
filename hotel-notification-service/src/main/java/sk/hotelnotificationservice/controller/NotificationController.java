package sk.hotelnotificationservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.hotelnotificationservice.dto.NotificationCreateDto;
import sk.hotelnotificationservice.dto.NotificationDto;
import sk.hotelnotificationservice.mapper.NotificationMapper;
import sk.hotelnotificationservice.repository.NotificationRepository;
import sk.hotelnotificationservice.service.NotificationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private NotificationService notificationService;
    private NotificationMapper notificationMapper;
    private NotificationRepository notificationRepository;

    public NotificationController(NotificationService notificationService, NotificationMapper notificationMapper,
                                  NotificationRepository notificationRepository) {
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
    }

    @ApiOperation(value = "Add notification")
    @PostMapping("/saveNotification")
    public ResponseEntity<NotificationDto> saveNotificationType(@RequestBody @Valid
                                                                        NotificationCreateDto notificationCreateDto) {
        return new ResponseEntity<>(notificationService.addNotification(notificationCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update notification")
    @PutMapping("/{id}/updateNotification")
    public ResponseEntity<NotificationDto> updateNotification(@PathVariable("id") Long id, @RequestBody @Valid
            NotificationCreateDto notificationCreateDto) {
        return new ResponseEntity<>(notificationService.updateNotification(id, notificationCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete notification")
    @DeleteMapping("/{id}/deleteNotification")
    public ResponseEntity<?> deleteNotification(@PathVariable("id") Long id) {
        notificationService.deleteNotificationById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
