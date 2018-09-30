package com.adeliosys.insurance.web;

import com.adeliosys.insurance.Utils;
import com.adeliosys.insurance.model.Company;
import com.adeliosys.insurance.repository.ReactiveCompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST endpoint used to manage (CRUD) insurance companies.
 */
@RestController
public class ReactiveCompanyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveCompanyController.class);

    private ReactiveCompanyRepository reactiveCompanyRepository;

    public ReactiveCompanyController(ReactiveCompanyRepository reactiveCompanyRepository) {
        this.reactiveCompanyRepository = reactiveCompanyRepository;
    }

    @GetMapping("/companies")
    Flux<Company> getCompanies() {
        LOGGER.info("Getting all companies");
        return reactiveCompanyRepository.findAll();
    }

    @GetMapping("/companies/{id}")
    Mono<Company> getCompany(@PathVariable String id, @RequestParam(name = "blockingPause", defaultValue = "0", required = false) long duration) {
        LOGGER.info("Getting company '{}'", id);
        Utils.pause(duration);
        return reactiveCompanyRepository.findById(id)
                .doOnNext(c -> LOGGER.info("Found company name '{}'", c.getName()));
    }
}
