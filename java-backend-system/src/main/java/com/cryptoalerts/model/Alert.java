package com.cryptoalerts.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Alert {
    private static final Logger logger = LoggerFactory.getLogger(Alert.class);

    private int id;
    private String symbol;
    private String basis;
    private Integer maLength;
    private double value;
    private String direction;
    private String status;

    public Alert(int id, String symbol, String basis, Integer maLength, double value, String direction, String status) {
        this.id = id;
        this.symbol = symbol;
        this.basis = basis;
        this.maLength = maLength;
        this.value = value;
        this.direction = direction;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBasis() {
        return basis;
    }

    public void setBasis(String basis) {
        this.basis = basis;
    }

    public Integer getMaLength() {
        return maLength;
    }

    public void setMaLength(Integer maLength) {
        this.maLength = maLength;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void implementPending() {
        if ("pending".equals(status)) {
            logger.info("Implementing pending operation for alert with ID: {}", id);
            // Perform the pending operation here
            status = "implemented";
        } else {
            logger.info("Alert is not pending. Status: {}", status);
        }
    }
}
