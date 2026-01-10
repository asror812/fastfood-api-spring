package com.example.app_fast_food.utils;

import java.io.IOException;

import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.example.app_fast_food.exception.entity.ErrorMessages;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            org.springframework.security.access.AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        String jsonResponse = "{\"message\": \"" + ErrorMessages.ACCESS_DENIED + "\", \"code\": \"ACCESS_DENIED\"}";
        response.getWriter().write(jsonResponse);
    }
}
