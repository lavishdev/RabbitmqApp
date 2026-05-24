package com.lavish.rabbitmqapp.controller;

import com.lavish.rabbitmqapp.service.MessageProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final MessageProducer messageProducer;


    @PostMapping("/email-notification")
    public ResponseEntity<Map<String, String>> sendEmailNotificatino(
            @RequestParam String recipient,
            @RequestParam String subject,
            @RequestParam String content,
            @RequestParam (defaultValue = "MEDIUM") String priority
    ) {
        log.info("Request to send Email notification to: {}", recipient);
        try{
            String messageId = messageProducer.sendEmailNotification(recipient, subject, content, priority);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "messageId", messageId,
                    "message", "Email notification sent to rabbitMq successfully"
            ));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));

        }
    }

    @PostMapping("/sms-notification")
    public ResponseEntity<Map<String, String>> sendSmsNotificatino(
            @RequestParam String recipient,
            @RequestParam String content
    ) {
        log.info("Request to send sms notification to: {}", recipient);
        try{
            String messageId = messageProducer.sendSmsNotification(recipient, content);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "messageId", messageId,
                    "message", "SMS notification sent to rabbitMq successfully"
            ));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));

        }
    }
}
