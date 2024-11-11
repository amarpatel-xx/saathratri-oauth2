package com.saathratri.orchestrator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.ArrayList;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtClaimsConverter jwtAuthConverter;

    @Value("${cors.allowed-origin}")
    private String allowedOrigin;

    public SecurityConfig(JwtClaimsConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
        logger.info("Initializing SecurityConfig with JwtClaimsConverter");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring Security Filter Chain");

        http.cors(cors -> cors.configurationSource(corsConfigurationSource())); // Configure CORS

        http.authorizeHttpRequests(authz -> {
            logger.info("Configuring authorization for /api/** endpoints to require authentication");
            authz
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll();  // Allow all other requests
        });

        http.oauth2ResourceServer(oauth2 -> {
            logger.info("Configuring OAuth2 Resource Server with JWT support");
            oauth2.jwt(jwt -> {
                jwt.jwtAuthenticationConverter(jwtAuthConverter);
                logger.info("Using JwtClaimsConverter for JWT authentication conversion");
            });
        });

        logger.info("Disabling CSRF protection for stateless sessions");
        http.csrf(AbstractHttpConfigurer::disable);

        logger.info("Setting session management to stateless");
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        logger.info("Security Filter Chain configuration complete");
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        List<String> allowedOrigins = new ArrayList<>();
        // Check if allowedOrigins is initialized and has values
        if (null == allowedOrigin) {
            logger.warn("No allowed origins configured for CORS. Using default origin: http://localhost:4200");
            allowedOrigins = List.of("http://localhost:4200"); // Default to localhost for development
        } else {
            logger.info("Configuring CORS with allowed origins: {}", allowedOrigin);
            allowedOrigins = List.of(allowedOrigin);
        }

        configuration.setAllowedOrigins(allowedOrigins); // Set allowed origins from application.yml
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allowed HTTP methods
        configuration.setAllowedHeaders(List.of("*")); // Allow all headers
        configuration.setAllowCredentials(true); // Allow credentials

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS configuration to all paths
        return source;
    }
}
