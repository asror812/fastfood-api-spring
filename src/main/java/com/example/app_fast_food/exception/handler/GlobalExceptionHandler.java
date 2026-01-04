package com.example.app_fast_food.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.app_fast_food.exception.AlreadyAddedToBasketException;
import com.example.app_fast_food.exception.AlreadyExistsException;
import com.example.app_fast_food.exception.EntityNotFoundException;
import com.example.app_fast_food.exception.FileNotFoundException;
import com.example.app_fast_food.exception.FileReadException;
import com.example.app_fast_food.exception.FileSizeLimitExceedException;
import com.example.app_fast_food.exception.InvalidCredentialsException;
import com.example.app_fast_food.exception.InvalidOperationException;
import com.example.app_fast_food.exception.OtpEarlyResentException;
import com.example.app_fast_food.exception.OtpLimitExitedException;
import com.example.app_fast_food.exception.PhoneNumberNotVerifiedException;
import com.example.app_fast_food.exception.UserBasketNotFoundException;
import com.example.app_fast_food.exception.dto.ErrorResponse;
import com.example.app_fast_food.exception.entity.ErrorMessages;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // OTP
    @ExceptionHandler(OtpLimitExitedException.class)
    public ResponseEntity<ErrorResponse> handleOtpLimitException(OtpLimitExitedException e) {
        log.error("OtpLimitExitedException: {}", e.getMessage());

        ErrorResponse response = new ErrorResponse(
                ErrorMessages.OTP_LIMIT,
                "OTP_LIMIT");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
    }

    @ExceptionHandler(OtpEarlyResentException.class)
    public ResponseEntity<ErrorResponse> handleOtpEarlyResentException(OtpEarlyResentException e) {
        log.error("OtpEarlyResentException: {}", e.getMessage());

        ErrorResponse response = new ErrorResponse(
                ErrorMessages.OTP_TOO_EARLY,
                "OTP_TOO_EARLY");
        return ResponseEntity.status(HttpStatus.TOO_EARLY).body(response);
    }

    @ExceptionHandler(PhoneNumberNotVerifiedException.class)
    public ResponseEntity<ErrorResponse> handlePhoneNumberNotVerified(PhoneNumberNotVerifiedException e) {
        log.error("PhoneNumberNotVerified: {}", e.getMessage());

        ErrorResponse response = new ErrorResponse(
                ErrorMessages.PHONE_NOT_VERIFIED,
                "PHONE_NOT_VERIFIED");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ENTITY
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException e) {
        log.error("EntityNotFoundException: {}", e.getMessage());

        ErrorResponse response = new ErrorResponse(
                ErrorMessages.ENTITY_NOT_FOUND,
                "NOT_FOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserBasketNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserBasketNotFound(UserBasketNotFoundException e) {
        log.error("UserBasketNotFoundException: {}", e.getMessage());

        ErrorResponse response = new ErrorResponse(
                ErrorMessages.USER_BASKET_NOT_FOUND,
                "NOT_FOUND");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // FILE
    @ExceptionHandler(FileSizeLimitExceedException.class)
    public ResponseEntity<ErrorResponse> handleFileSizeLimit(FileSizeLimitExceedException e) {
        log.error("FileSizeLimitExceedException: {}", e.getMessage());

        ErrorResponse response = new ErrorResponse(
                ErrorMessages.FILE_TOO_LARGE,
                "FILE_TOO_LARGE");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(FileReadException.class)
    public ResponseEntity<String> handleFileRead(FileReadException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleNotFound(FileNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    // Entity
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExist(AlreadyExistsException e) {
        log.error("AlreadyExistException: {}", e.getMessage());

        ErrorResponse response = new ErrorResponse(
                ErrorMessages.ALREADY_EXIST,
                "ALREADY_EXIST");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOperation(InvalidOperationException e) {
        log.error("InvalidOperationException: {}", e.getMessage());

        ErrorResponse response = new ErrorResponse(
                ErrorMessages.INVALID_OPERATION,
                "INVALID_OPERATION");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.error("IllegalArgumentException: {}", ex.getMessage());

        ErrorResponse response = new ErrorResponse(
                ErrorMessages.INVALID_ARGUMENT,
                "INVALID_ARGUMENT");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        log.error("InvalidCredentialsException: {}", ex.getMessage());

        ErrorResponse response = new ErrorResponse(
                ErrorMessages.INVALID_CREDENTIALS,
                "INVALID_CREDENTIALS");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        log.warn("AccessDenied: {}", ex.getMessage());

        ErrorResponse response = new ErrorResponse(
                ErrorMessages.ACCESS_DENIED,
                "ACCESS_DENIED");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException: {}", ex.getMessage());

        ErrorResponse response = new ErrorResponse();
        response.setCode("INVALID_REQUEST");
        response.setMessage("Validation failed");

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        response.setErrors(errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AlreadyAddedToBasketException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyAddedToBasket(AlreadyAddedToBasketException ex) {
        log.error("AlreadyAddedToBasketException: {}", ex.getMessage());

        ErrorResponse response = new ErrorResponse(ErrorMessages.ALREADY_ADDED_TO_BASKET, "ALREADY EXISTS");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception e) {
        log.error("Unhandled Exception: {}", e.getMessage());

        ErrorResponse response = new ErrorResponse(
                "Internal server error",
                "INTERNAL_ERROR");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
