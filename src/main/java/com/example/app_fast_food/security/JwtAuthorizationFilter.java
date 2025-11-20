package com.example.app_fast_food.security;

import com.example.app_fast_food.exceptions.dto.ErrorResponse;
import com.example.app_fast_food.exceptions.entity.ErrorMessages;
import com.example.app_fast_food.user.UserRepository;
import com.example.app_fast_food.user.entity.User;
import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final Gson gson;

    public static final Set<String> EXCLUDED_URLS = Set.of(
            "/auth",
            "/v3/api-docs",
            "/swagger-ui",
            "/products",
            "/categories",
            "orders");

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (EXCLUDED_URLS.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendError(response, ErrorMessages.TOKEN_INVALID, "TOKEN_MISSING");
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtService.claims(token);
            String phoneNumber = claims.getSubject();

            if (phoneNumber == null) {
                sendError(response, ErrorMessages.TOKEN_INVALID, "TOKEN_INVALID");
                return;
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                User user = userRepository.findByPhoneNumber(phoneNumber)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "User with phone %s not found".formatted(phoneNumber)));

                if (!jwtService.isTokenValid(token, phoneNumber)) {
                    sendError(response, ErrorMessages.TOKEN_INVALID, "TOKEN_INVALID");
                    return;
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (EntityNotFoundException e) {
            log.error("User not found: {}", e.getMessage());
            sendError(response, ErrorMessages.USER_NOT_FOUND, "USER_NOT_FOUND");
            return;

        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            sendError(response, ErrorMessages.AUTH_FAILED, "AUTH_FAILED");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendError(HttpServletResponse response, String message, String code) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse error = new ErrorResponse(message, code);
        response.getWriter().write(gson.toJson(error));
    }
}
