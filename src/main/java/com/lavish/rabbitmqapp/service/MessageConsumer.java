package com.lavish.rabbitmqapp.service;

import com.lavish.rabbitmqapp.config.RabbitMqConfig;
import com.lavish.rabbitmqapp.entity.Message;
import com.lavish.rabbitmqapp.entity.Notification;
import com.lavish.rabbitmqapp.repository.NotificationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j

public class MessageConsumer {
    @Autowired
    private NotificationRepo notificationRepo;

    @RabbitListener(queues = RabbitMqConfig.EMAIL_QUEUE)
    public void HandleEmail(Message message) {
        log.info("Email Message Received: Id={}, Recipient={}, subject={}",
                message.getId(), message.getRecipient(), message.getSubject());

        try{
            Thread.sleep(2000);
            Notification notification = Notification.builder()
                    .messageId(message.getId())
                    .type(message.getType())
                    .recipient(message.getRecipient())
                    .subject(message.getSubject())
                    .content(message.getContent())
                    .status("SENT")
                    .processedAt(LocalDateTime.now())
                    .build();
            notificationRepo.save(notification);
            log.info("Email notification message processed and save with Id: {}", message.getId());
        }
        catch (Exception e){
            log.error("Failed to processed the message: {}", e.getMessage(), e);
            Notification notification = Notification.builder()
                    .messageId(message.getId())
                    .type(message.getType())
                    .recipient(message.getRecipient())
                    .subject(message.getSubject())
                    .content(message.getContent())
                    .status("FAILED")
                    .processedAt(LocalDateTime.now())
                    .build();
            notificationRepo.save(notification);
        }
    }


    @RabbitListener(queues = RabbitMqConfig.SMS_QUEUE)
    public void HandleSms(Message message) {
        log.info("Sms Message Received: Id={}, Recipient={}, content={}",
                message.getId(), message.getRecipient(), message.getContent());

        try{
            Thread.sleep(500);
            Notification notification = Notification.builder()
                    .messageId(message.getId())
                    .type(message.getType())
                    .recipient(message.getRecipient())
                    .subject(message.getSubject())
                    .content(message.getContent())
                    .status("SENT")
                    .processedAt(LocalDateTime.now())
                    .build();
            notificationRepo.save(notification);
            log.info("Sms notification message processed and save with Id: {}", message.getId());
        }
        catch (Exception e){
            log.error("Failed to processed the message: {}", e.getMessage(), e);
            Notification notification = Notification.builder()
                    .messageId(message.getId())
                    .type(message.getType())
                    .recipient(message.getRecipient())
                    .subject(message.getSubject())
                    .content(message.getContent())
                    .status("FAILED")
                    .processedAt(LocalDateTime.now())
                    .build();
            notificationRepo.save(notification);
        }
    }

}
