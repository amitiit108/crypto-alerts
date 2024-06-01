package com.cryptoalerts.model;

public class Alert {
    private int id;
    private String symbol;
    private String basis;
    private Integer maLength;
    private double value;
    private String direction;
    private String status;

    // Constructor
    public Alert(int id, String symbol, String basis, Integer maLength, double value, String direction, String status) {
        this.id = id;
        this.symbol = symbol;
        this.basis = basis;
        this.maLength = maLength;
        this.value = value;
        this.direction = direction;
        this.status = status;
    }

    // Getters and Setters
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

    // Method to implement pending operation
    public void implementPending() {
        if (status.equals("pending")) {
            // Perform the pending operation here
            System.out.println("Implementing pending operation for alert with ID: " + id);
            // Update status after implementation
            status = "implemented";
        } else {
            // Do nothing if status is not pending
            System.out.println("Alert is not pending. Status: " + status);
        }
    }
}
