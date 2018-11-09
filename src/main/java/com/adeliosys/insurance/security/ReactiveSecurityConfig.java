package com.adeliosys.insurance.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Spring Security configuration.
 */
@EnableWebFluxSecurity
public class ReactiveSecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveSecurityConfig.class);

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        LOGGER.info("Initializing the security configuration");
        return http.authorizeExchange()
                .pathMatchers("/companies").hasRole("USER")
                .anyExchange().permitAll()
                .and().httpBasic()
                .and().build();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
