package com.cryptoalerts.dto;

public class AlertResponse {
    private String message;

    public AlertResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
