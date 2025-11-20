package com.example.app_fast_food.utils;

import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class TransactionLoggerFilter extends OncePerRequestFilter {
    private static final String TRANSACTION_ID = "transactionID";
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLoggerFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String transactionID = UUID.randomUUID().toString();
        MDC.put(TRANSACTION_ID, transactionID);

        long startTime = System.currentTimeMillis();
        LOGGER.info("Transaction id {} | HTTP {} - {}", transactionID, request.getMethod(), request.getRequestURI());

        try {
            filterChain.doFilter(request, response);

            long duration = System.currentTimeMillis() - startTime;
            LOGGER.info("Transaction id {} | Response Status: {} | Time Taken: {}ms",
                    transactionID, response.getStatus(), duration);

        } finally {
            MDC.remove(TRANSACTION_ID);
        }

    }
}