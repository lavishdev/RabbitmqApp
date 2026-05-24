package com.lavish.rabbitmqapp.service;

import com.lavish.rabbitmqapp.config.RabbitMqConfig;
import com.lavish.rabbitmqapp.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public String sendEmailNotification(String recipient, String subject, String content, String priority) {
        String messageId = UUID.randomUUID().toString();

        Message message = Message.builder()
                .id(messageId)
                .type("EMAIL")
                .recipient(recipient)
                .subject(subject)
                .content(content)
                .priority("HIGH")
                .timestamp(LocalDateTime.now())
                .build();

        try {
            log.info("Sending email notification with Id: {} to RabbitMq", messageId);
            rabbitTemplate.convertAndSend(
                    RabbitMqConfig.APP_EXCHANGE,
                    RabbitMqConfig.Email_NOTIFICATION_ROUTING_KEY,
                    message);
            log.info("Email-Notification sent to the Queue");
            return messageId;

        }
        catch (Exception e) {
            log.error("Failed to send email message: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send Email message");
        }
    }

    public String sendSmsNotification(String recipient, String content) {

        String messageId = UUID.randomUUID().toString();

        Message message = Message.builder()
                .id(messageId)
                .type("SMS")
                .recipient(recipient)
                .subject("Sms Notification")
                .content(content)
                .priority("HIGH")
                .timestamp(LocalDateTime.now())
                .build();

        try {
            log.info("Sending sms notification with Id: {} to RabbitMq", messageId);
            rabbitTemplate.convertAndSend(
                    RabbitMqConfig.APP_EXCHANGE,
                    RabbitMqConfig.SMS_NOTIFICATION_ROUTING_KEY,
                    message);
            log.info("Sms-Notification sent to the Queue");
            return messageId;

        }
        catch (Exception e) {
            log.error("Failed to send sms message: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send sms message");
        }
    }
}
