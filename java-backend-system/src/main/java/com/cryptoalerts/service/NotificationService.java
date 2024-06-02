package com.cryptoalerts.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendNotification(String message) {
        try {
            
            // Implementation to send notifications (e.g., email, push notifications)
            
            // Log notification sending
            logger.info("Notification sent: {}", message);
        } catch (Exception e) {
            // Log any errors that occur during notification sending
            logger.error("Error sending notification: {}", e.getMessage());
            e.printStackTrace(); // For demonstration purposes, print the stack trace
        }
    }
}
