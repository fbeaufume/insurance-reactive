package com.adeliosys.insurance.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Sample request scoped service, used to test scopes in reactive programming.
 */
@Service
@RequestScope
public class Counter {

    private Logger LOGGER = LoggerFactory.getLogger(Counter.class);

    private int count = 0;

    /**
     * When called from a reactive method, will throw a
     * java.lang.IllegalStateException: No Scope registered for scope name 'request'
     */
    public void increment() {
        LOGGER.info("Count is {}", ++count);
    }
}
