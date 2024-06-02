package com.cryptoalerts.service;

import com.cryptoalerts.model.Alert;
import com.cryptoalerts.model.AlertStatus;
import com.cryptoalerts.repository.AlertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class AlertService {
    private static final Logger logger = LoggerFactory.getLogger(AlertService.class);

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public void createAlert(Alert alert) {
        try {
            alert.setStatus(AlertStatus.PENDING.name());
            alertRepository.save(alert);
            logger.info("Alert created successfully: {}", alert);
        } catch (Exception e) {
            logger.error("Error creating alert: {}", e.getMessage());
            e.printStackTrace(); // For demonstration purposes, print the stack trace
        }
    }

    @Transactional(readOnly = true) // Optional, but recommended for read-only operations
    public List<Alert> getAllAlerts() {
        try {
            List<Alert> alerts = alertRepository.findAll();
            logger.info("Retrieved {} alerts from the database.", alerts.size());
            return alerts;
        } catch (Exception e) { // Catch a broader exception for flexibility
            logger.error("Error fetching alerts: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void removeAlert(int id) {
        try {
            alertRepository.delete(id);
            logger.info("Alert with ID {} deleted successfully.", id);
        } catch (SQLException e) {
            logger.error("Error deleting alert: {}", e.getMessage());
            e.printStackTrace(); // For demonstration purposes, print the stack trace
        }
    }

    public void updateAlertStatus(int id, AlertStatus status) {
        try {
            alertRepository.updateStatus(id, status.name());
            logger.info("Alert status updated successfully for ID {}: {}", id, status);
        } catch (SQLException e) {
            logger.error("Error updating alert status: {}", e.getMessage());
            e.printStackTrace(); // For demonstration purposes, print the stack trace
        }
    }
}
