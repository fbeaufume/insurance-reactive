package com.adeliosys.insurance.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Request filter that logs the request duration in the logs.
 * Adding a request ID in all application logs through SLF4J MDC
 * does not work since multiple threads are involved when processing
 * the request.
 */
@Component
public class ReactiveLogFilter implements WebFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveLogFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long timestamp = System.currentTimeMillis();

        LOGGER.info("Serving {} '{}'", exchange.getRequest().getMethod(), exchange.getRequest().getURI());

        return chain.filter(exchange).doAfterTerminate(() -> {
            HttpStatus status = exchange.getResponse().getStatusCode();
            LOGGER.info("Served {} '{}' as {} in {} msec",
                    exchange.getRequest().getMethod(),
                    exchange.getRequest().getURI(),
                    // WebFlux sets the default 200 status later than this callback,
                    // see https://stackoverflow.com/questions/52668050/spring-webflux-statuscode-is-null-in-a-webfilter
                    // and https://jira.spring.io/browse/SPR-17368
                    status == null ? 200 : status.value(),
                    System.currentTimeMillis() - timestamp);
        });
    }
}
