package com.adeliosys.insurance.security;

import com.adeliosys.insurance.repository.ReactiveUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Service used to load the user details during authentication.
 */
@Service
public class ReactiveUserDetailsRepository implements ReactiveUserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveUserDetailsRepository.class);

    private ReactiveUserRepository reactiveUserRepository;

    public ReactiveUserDetailsRepository(ReactiveUserRepository reactiveUserRepository) {
        LOGGER.info("Initializing the user details repository");
        this.reactiveUserRepository = reactiveUserRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        LOGGER.info("Loading details for user '{}'", username);
        return reactiveUserRepository.findByUsername(username).cast(UserDetails.class);
    }
}
