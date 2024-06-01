package com.cryptoalerts.service;

import com.cryptoalerts.config.BinanceAPIConfig;
import com.cryptoalerts.model.Alert;
import com.cryptoalerts.model.AlertStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.TimerTask;

public class PriceCheckerService extends TimerTask {
    private final AlertService alertService;
    private final BinanceAPIConfig binanceAPIConfig;
    private final NotificationService notificationService;

    public PriceCheckerService(AlertService alertService, BinanceAPIConfig binanceAPIConfig, NotificationService notificationService) {
        this.alertService = alertService;
        this.binanceAPIConfig = binanceAPIConfig;
        this.notificationService = notificationService;
    }

    @Override
    public void run() {
        try {
            List<Alert> alerts = alertService.getAllAlerts();
            for (Alert alert : alerts) {
                String priceResponse = binanceAPIConfig.getPrice(alert.getSymbol());
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(priceResponse);
                double currentPrice = rootNode.get("price").asDouble();
                boolean isTriggered = false;

                if (alert.getBasis().equals("5000")) {
                    if ("UP".equals(alert.getDirection()) && currentPrice >= alert.getValue()) {
                        isTriggered = true;
                    } else if ("DOWN".equals(alert.getDirection()) && currentPrice <= alert.getValue()) {
                        isTriggered = true;
                    }
                }

                if (isTriggered) {
                    alertService.updateAlertStatus(alert.getId(), AlertStatus.COMPLETED);
                    notificationService.sendNotification("Alert triggered for symbol: " + alert.getSymbol() + ", Current Price: " + currentPrice + ", Value: " + alert.getValue() + ", Alert Status: " + alert.getStatus());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
