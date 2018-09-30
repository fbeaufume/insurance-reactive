# Insurance Reactive

This is the reactive version of the Insurance sample application.

See insurance-blocking project for the blocking version.

It is based on Spring Boot 2, Spring 5, Spring WebFlux, Spring Data, Reactor, Spring Security 5, MongoDB.

Run with `mvn spring-boot:run`.

Then use to:
- <http://localhost:8082/init> to initialize the data
- <http://localhost:8082/companies> to list the insurance companies (this URL is secured)
- <http://localhost:8082/quote/1> to get the quote for a given company
- <http://localhost:8082/bestQuote> to get the best quote from registered companies
