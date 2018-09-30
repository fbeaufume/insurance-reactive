package com.adeliosys.insurance.web;

import com.adeliosys.insurance.Utils;
import com.adeliosys.insurance.model.Company;
import com.adeliosys.insurance.model.Quote;
import com.adeliosys.insurance.repository.ReactiveCompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * REST endpoint used to provide insurance quotes.
 */
@RestController
public class ReactiveQuoteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveQuoteController.class);

    private ReactiveCompanyRepository reactiveCompanyRepository;

    private WebClient client = WebClient.create();

    @Value("${server.port}")
    private int serverPort = 8080;

    public ReactiveQuoteController(ReactiveCompanyRepository reactiveCompanyRepository) {
        this.reactiveCompanyRepository = reactiveCompanyRepository;
    }

    /**
     * Simulate an insurance backend by returning an insurance quote.
     */
    @GetMapping("/quote/{id}")
    public Mono<Quote> getQuote(@PathVariable String id, @RequestParam(name = "pause", defaultValue = "0", required = false) long duration) {
        LOGGER.info("Simulating quote for company '{}'", id);

        return reactiveCompanyRepository.findById(id)
                .map(Quote::new)
                .delayElement(Duration.ofMillis(duration))
                .doOnNext(q -> LOGGER.info("Simulated quote for company '{}'", id));
    }

    /**
     * Return the best quote from the registered insurance companies.
     */
    @GetMapping("/bestQuote")
    public Mono<Quote> getBestQuote(@RequestParam(name = "pause", defaultValue = "0", required = false) long duration) {
        long timestamp = System.nanoTime();
        LOGGER.info("Getting the best quote");

        return reactiveCompanyRepository.findAll()
                .flatMap(c -> getRemoteQuote(c, duration))
                .sort()
                .take(1)
                .single()
                .doOnNext(q -> LOGGER.info("Found best quote cost of {} in {} msec", q.getCost(), Utils.getDuration(timestamp)));
    }

    /**
     * Get a remote quotation using an HTTP call.
     */
    private Mono<Quote> getRemoteQuote(Company company, long duration) {
        String url = "http://localhost:" + serverPort + "/quote/" + company.getId() + "?pause=" + duration;
        LOGGER.info("Getting quote for company '{}' from '{}'", company.getId(), url);

        return client.get()
                .uri(url)
                .accept(APPLICATION_JSON)
                .exchange()
                .flatMap(resp -> resp.bodyToMono(Quote.class));
    }
}
