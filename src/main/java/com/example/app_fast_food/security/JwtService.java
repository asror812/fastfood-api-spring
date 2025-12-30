package com.example.app_fast_food.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.app_fast_food.user.entity.User;
import com.example.app_fast_food.user.permission.Permission;
import com.example.app_fast_food.user.role.Role;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final TokenProperties tokenProperties;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiration = Date.from(now.toInstant().plusSeconds(tokenProperties.getExpiration()));

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        List<String> perms = user.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream())
                .map(Permission::getName)
                .distinct()
                .toList();

        Map<String, Object> claims = new HashMap<>();

        claims.put("roles", roles);
        claims.put("perms", perms);
        claims.put("phone", user.getPhoneNumber());

        return Jwts.builder()
                .subject(user.getId().toString())
                .claims(claims)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(signingKey())
                .compact();
    }

    public boolean isTokenValid(String token, UUID id) {
        try {
            final UUID userId = extractUserId(token);
            return (userId.equals(id) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(extractClaim(token, Claims::getSubject));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    public String extractPhoneNumber(String token) {
        return extractClaim(token, claims -> claims.get("phone", String.class));
    }

    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> ((List<?>) claims.getOrDefault("roles", List.of()))
                .stream()
                .map(String::valueOf)
                .toList());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = claims(token);
        return claimsResolver.apply(claims);
    }

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(tokenProperties.getSecretKey().getBytes());
    }

    public Claims claims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public List<String> extractPermissions(String token) {
        return extractClaim(token, claims -> ((List<?>) claims.getOrDefault("perms", List.of()))
                .stream()
                .map(String::valueOf)
                .toList());
    }

}
