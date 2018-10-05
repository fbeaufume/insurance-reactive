package com.adeliosys.insurance.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Sample request scoped service, used to test scopes in reactive programming.
 * <p>
 * Not supported by reactive libs from Spring Boot 2.0.4, fails with
 * "java.lang.IllegalStateException: No Scope registered for scope name 'request'".
 * <p>
 * Same for @SessionScoped.
 */
@Service
@RequestScope
public class Counter {

    private Logger LOGGER = LoggerFactory.getLogger(Counter.class);

    private int count = 0;

    public void increment() {
        LOGGER.info("Count is {}", ++count);
    }
}
