package com.cryptoalerts.service;

import com.cryptoalerts.config.BinanceAPIConfig;
import com.cryptoalerts.model.Alert;
import com.cryptoalerts.model.AlertStatus;
import com.cryptoalerts.AlertWebSocketServer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceCheckerService extends TimerTask {
    private static final Logger logger = LoggerFactory.getLogger(PriceCheckerService.class);

    private final AlertService alertService;
    private final BinanceAPIConfig binanceAPIConfig;
    private final NotificationService notificationService;
    private final Set<AlertWebSocketServer> webSocketServers;

    public PriceCheckerService(AlertService alertService, BinanceAPIConfig binanceAPIConfig,
                               NotificationService notificationService, Set<AlertWebSocketServer> webSocketServers) {
        this.alertService = alertService;
        this.binanceAPIConfig = binanceAPIConfig;
        this.notificationService = notificationService;
        this.webSocketServers = webSocketServers;
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

                if (alert.getBasis().equals("price")) {
                    if ("UP".equals(alert.getDirection()) && currentPrice >= alert.getValue()) {
                        isTriggered = true;
                    } else if ("DOWN".equals(alert.getDirection()) && currentPrice <= alert.getValue()) {
                        isTriggered = true;
                    }
                }

                if (isTriggered) {
                    alertService.updateAlertStatus(alert.getId(), AlertStatus.COMPLETED);
                    String alertMessage = "Alert triggered for symbol: " + alert.getSymbol() +
                            ", Current Price: " + currentPrice +
                            ", Value: " + alert.getValue() +
                            ", Alert Status: " + alert.getStatus();

                    // Send notification
                    notificationService.sendNotification(alertMessage);
                    // Broadcast alert message to WebSocket clients
                    for (AlertWebSocketServer server : webSocketServers) {
                        server.broadcast(alertMessage);
                    }

                    logger.info("Alert triggered: {}", alertMessage);
                }
            }
        } catch (Exception e) {
            logger.error("Error in PriceCheckerService: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
