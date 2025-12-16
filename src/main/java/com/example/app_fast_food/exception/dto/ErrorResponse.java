package com.example.app_fast_food.exception.dto;

public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
