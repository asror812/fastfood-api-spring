package com.example.app_fast_food.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.app_fast_food.utils.TransactionLoggerFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity httpSecurity,
                        JwtAuthorizationFilter jwtAuthorizationFilter,
                        TransactionLoggerFilter transactionLoggerFilter) throws Exception {
                return httpSecurity
                                .csrf(AbstractHttpConfigurer::disable)
                                .cors(AbstractHttpConfigurer::disable)
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(registry -> registry
                                                // .requestMatchers(
                                                // "/auth/**",
                                                // "/swagger-ui/**",
                                                // "/v3/**")
                                                // .permitAll()
                                                .requestMatchers("/**")
                                                .permitAll())
                                // .anyRequest().authenticated())
                                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                                .addFilterBefore(transactionLoggerFilter, JwtAuthorizationFilter.class)
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(
                                                                (req, res, e) -> res.sendError(401, "Unauthorized"))
                                                .accessDeniedHandler((req, res, e) -> res.sendError(403, "Forbidden")))
                                .build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
