package com.adeliosys.insurance.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

    /**
     * Sample in-memory user details service.
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        LOGGER.info("Initializing the user details service");
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("pass")
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }
}
