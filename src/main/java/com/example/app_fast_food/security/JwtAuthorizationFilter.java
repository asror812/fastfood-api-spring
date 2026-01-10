package com.example.app_fast_food.security;

import com.example.app_fast_food.exception.dto.ErrorResponse;
import com.example.app_fast_food.exception.entity.ErrorMessages;
import com.example.app_fast_food.user.dto.AuthDto;
import com.google.gson.Gson;

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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final Gson gson;

    public static final Set<String> EXCLUDED_URLS = Set.of(
            "/auth",
            "/v3/api-docs",
            "/swagger-ui");

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
            filterChain.doFilter(request, response);
            return;
        }

        if (!authHeader.startsWith("Bearer ")) {
            sendError(response, ErrorMessages.TOKEN_INVALID, "TOKEN_INVALID_FORMAT");
            return;
        }

        String token = authHeader.substring(7);

        try {
            UUID userId = jwtService.extractUserId(token);
            String phoneNumber = jwtService.extractPhoneNumber(token);

            if (userId == null || !jwtService.isTokenValid(token, userId)) {
                sendError(response, ErrorMessages.TOKEN_INVALID, "TOKEN_INVALID");
                return;
            }

            List<String> roles = jwtService.extractRoles(token);
            List<String> perms = jwtService.extractPermissions(token);

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();

            if (roles != null) {
                roles.forEach(r -> authorities.add(new SimpleGrantedAuthority("ROLE_" + r)));
            }
            if (perms != null) {
                perms.forEach(p -> authorities.add(new SimpleGrantedAuthority(p)));
            }

            AuthDto auth = new AuthDto(userId, phoneNumber);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    auth, null, authorities);

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            sendError(response, ErrorMessages.AUTH_FAILED, "AUTH_FAILED");
        }
    }

    private void sendError(HttpServletResponse response, String message, String code) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse error = new ErrorResponse(message, code);
        response.getWriter().write(gson.toJson(error));
    }
}
