package com.api.services;

import com.api.filters.LoggingFilter;
import com.api.utilities.ConfigReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;

public class BaseService {

    private static final String BASE_URL = ConfigReader.get("base.url");
    private static final ThreadLocal<RequestSpecification> requestSpec = new ThreadLocal<>();

    private static final ThreadLocal<String> authToken = ThreadLocal.withInitial(() ->
            ConfigReader.get("auth.token")
    );

    protected RequestSpecification getRequestSpec() {
        RequestSpecification spec = requestSpec.get();
        if (spec == null) {
            spec = given()
                    .baseUri(BASE_URL)
                    .filter(new LoggingFilter());
            requestSpec.set(spec);
        }
        return spec;
    }

    public void clearSpec() {
        requestSpec.remove();
        authToken.remove();
    }

    protected Map<String, String> authHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-auth-token", authToken.get());
        return headers;
    }

    protected Response getRequest(String endpoint) {
        return getRequestSpec().get(endpoint);
    }

    protected Response getRequest(String endpoint, Map<String, String> headers) {
        return getRequestSpec().headers(headers).get(endpoint);
    }

    protected Response postRequest(Object payload, String endpoint) {
        return getRequestSpec()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(endpoint);
    }

    protected Response postRequest(Object payload, String endpoint, Map<String, String> headers) {
        return getRequestSpec()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(payload)
                .post(endpoint);
    }

    protected Response patchRequest(Object payload, String endpoint) {
        return getRequestSpec()
                .contentType(ContentType.JSON)
                .body(payload)
                .patch(endpoint);
    }

    protected Response patchRequest(Object payload, String endpoint, Map<String, String> headers) {
        return getRequestSpec()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(payload)
                .patch(endpoint);
    }

    protected Response putRequest(Object payload, String endpoint, Map<String, String> headers) {
        return getRequestSpec()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(payload)
                .put(endpoint);
    }

    protected Response deleteRequest(String endpoint, Map<String, String> headers) {
        return getRequestSpec()
                .headers(headers)
                .delete(endpoint);
    }
}