package com.lavish.rabbitmqapp.controller;

import com.lavish.rabbitmqapp.entity.Notification;
import com.lavish.rabbitmqapp.repository.NotificationRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/notifications")
public class NotificationController {

    private final NotificationRepo notificationRepo;

    @GetMapping("/getAllnotification")
    public ResponseEntity<List<Notification>> getAllNotificationByRecipient() {
        List<Notification> notifications = notificationRepo.findAll();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/getAllnotificationbyrecipient")
    public ResponseEntity<List<Notification>> getNotificationsByRecipient(String recipient) {
        List<Notification> notificationsbyrecipient = notificationRepo.findByRecipientOrderByCreatedAtDesc(recipient);
        return ResponseEntity.ok(notificationsbyrecipient);
    }
}
