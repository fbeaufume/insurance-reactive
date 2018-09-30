package com.adeliosys.insurance.web;

import com.adeliosys.insurance.model.Company;
import com.adeliosys.insurance.model.User;
import com.adeliosys.insurance.repository.ReactiveCompanyRepository;
import com.adeliosys.insurance.repository.ReactiveUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * REST endpoint for various utility methods.
 */
@RestController
public class ReactiveMiscController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveMiscController.class);

    private ReactiveUserRepository reactiveUserRepository;

    private ReactiveCompanyRepository reactiveCompanyRepository;

    public ReactiveMiscController(
            ReactiveUserRepository reactiveUserRepository,
            ReactiveCompanyRepository reactiveCompanyRepository) {
        this.reactiveUserRepository = reactiveUserRepository;
        this.reactiveCompanyRepository = reactiveCompanyRepository;
    }

    /**
     * Pause for a given duration in msec.
     */
    @GetMapping("/pause/{duration}")
    public Mono<Void> pause(@PathVariable long duration) {
        return Mono.delay(Duration.ofMillis(duration)).then();
    }

    /**
     * Same with logs.
     */
    @GetMapping("/pause2/{duration}")
    public Mono<Void> pause2(@PathVariable long duration) {
        LOGGER.info("Pausing for {} msec", duration);
        return Mono.delay(Duration.ofMillis(duration))
                .doFinally(s -> LOGGER.info("Paused for {} msec", duration))
                .then();
    }

    /**
     * Initialize the persistent data of the application.
     */
    @GetMapping("/init")
    Mono<Void> initialize() {
        LOGGER.info("Initializing the users");
        return reactiveUserRepository.deleteAll()
                .then(reactiveUserRepository.insert(Flux.fromIterable(User.getDefaultUsers())).then())
                .doOnSuccess(v -> LOGGER.info("Initializing the companies"))
                .then(reactiveCompanyRepository.deleteAll())
                .then(reactiveCompanyRepository.insert(Flux.fromIterable(Company.getDefaultCompanies())).then())
                .doOnSuccess(v -> LOGGER.info("Initialized the data"));
    }
}
