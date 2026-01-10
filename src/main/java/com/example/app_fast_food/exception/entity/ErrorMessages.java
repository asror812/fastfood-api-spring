package com.example.app_fast_food.exception.entity;

public class ErrorMessages {

    public static final String OTP_LIMIT = "Too many OTP requests. Please wait before trying again.";
    public static final String OTP_TOO_EARLY = "You must wait before resending OTP.";

    public static final String PHONE_NOT_VERIFIED = "Phone number is not verified.";

    public static final String FILE_TOO_LARGE = "Uploaded file exceeds the allowed size.";

    public static final String ENTITY_NOT_FOUND = "Requested resource was not found.";
    public static final String USER_BASKET_NOT_FOUND = "Your Basket was not found";
    public static final String INVALID_OPERATION = "Operation is not allowed.";

    public static final String TOO_FAR = "Your location is too far from the restaurant.";

    public static final String TOKEN_INVALID = "Invalid or expired token.";
    public static final String AUTH_FAILED = "Authentication failed.";
    public static final String USER_NOT_FOUND = "User not found.";

    public static final String PHONE_ALREADY_REGISTERED = "This phone number is already registered";
    public static final String ALREADY_EXIST = "Resource already exists";
    public static final String INVALID_ARGUMENT = "Invalid request params";
    public static final String ALREADY_ADDED_TO_BASKET = "Already added to basket";
    public static final String INVALID_CREDENTIALS = "Invalid phoneNumber or password";
    public static final String ACCESS_DENIED = "You do not have permission for this";

    private ErrorMessages() {
    }
}
