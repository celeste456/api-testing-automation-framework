package com.api.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {
        long threadId = Thread.currentThread().getId();
        logger.info("======= API CALL START =======");
        logger.info("=> {} {}", requestSpec.getMethod(), requestSpec.getURI());
        logger.info("Headers: {}", requestSpec.getHeaders());

        if (requestSpec.getBody() != null) {
            logger.info("Request Body: {}", (Object) requestSpec.getBody());
        }

        Response response = ctx.next(requestSpec, responseSpec);

        logger.info("Status Code: {}", response.getStatusCode());
        logger.info("Response time: {} ms", response.getTime());

        if (response.getStatusCode() >= 400) {
            logger.error("⚠️ Error response body:\n{}", (Object) response.getBody().asPrettyString());
        } else {
            logger.info("Response Body:\n{}", (Object) response.getBody().asPrettyString());
        }

        logger.info("======= API CALL END =========\n");

        return response;
    }

    private void logRequest(FilterableRequestSpecification requestSpec) {
        logger.info("Request: {} {}", requestSpec.getMethod(), requestSpec.getURI());

        requestSpec.getHeaders().asList().forEach(header ->
                logger.info("Header: {} = {}", header.getName(), header.getValue())
        );
    }

    private void logResponse(Response response) {
        logger.info("Response Status: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.getBody().asPrettyString());
    }

}
