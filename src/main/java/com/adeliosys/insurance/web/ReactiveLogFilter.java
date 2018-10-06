package com.adeliosys.insurance.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Servlet filter that logs the request duration in the logs.
 * Adding a request ID in all application logs through SLF4J MDC
 * does not work as multiple threads are involved when processing
 * the request.
 */
@Component
public class ReactiveLogFilter implements WebFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveLogFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long timestamp = System.currentTimeMillis();

        LOGGER.info("Serving {} '{}'", exchange.getRequest().getMethod(), exchange.getRequest().getURI());

        return chain.filter(exchange).doAfterTerminate(() -> LOGGER.info("Served {} '{}' as {} in {} msec",
                exchange.getRequest().getMethod(),
                exchange.getRequest().getURI(),
                // TODO FBE status code is null, see org.springframework.http.server.reactive.AbstractServerHttpResponse:get/setStatusCode
                exchange.getResponse().getStatusCode(),
                System.currentTimeMillis() - timestamp));
    }
}
