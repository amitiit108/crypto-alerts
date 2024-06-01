package com.cryptoalerts.service;

import com.cryptoalerts.model.Alert;
import com.cryptoalerts.model.AlertStatus;
import com.cryptoalerts.repository.AlertRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class AlertService {
    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public void createAlert(Alert alert) {
        try {
            alert.setStatus(AlertStatus.PENDING.name());
            alertRepository.save(alert);
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            e.printStackTrace(); // For demonstration purposes, print the stack trace
        } 
    }

    @Transactional(readOnly = true) // Optional, but recommended for read-only operations
    public List<Alert> getAllAlerts() {
        try {
            return alertRepository.findAll();
        } catch (Exception e) { // Catch a broader exception for flexibility
            return Collections.emptyList();
        }
    }

    public void removeAlert(int id) throws SQLException {
        alertRepository.delete(id);
    }

    public void updateAlertStatus(int id, AlertStatus status) throws SQLException {
        alertRepository.updateStatus(id, status.name());
    }
}
