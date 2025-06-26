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
    protected RequestSpecification requestSpecification;

    public BaseService() {
        requestSpecification = given()
                .baseUri(BASE_URL)
                .filter(new LoggingFilter());
    }

    protected Map<String, String> authHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-auth-token", ConfigReader.get("auth.token"));
        return headers;
    }

    protected Response getRequest(String endpoint) {
        return requestSpecification.get(endpoint);
    }

    protected Response getRequest(String endpoint, Map<String, String> headers) {
        return requestSpecification
                .headers(headers)
                .get(endpoint);
    }

    protected Response postRequest(Object payload, String endpoint) {
        return requestSpecification
                .contentType(ContentType.JSON)
                .body(payload)
                .post(endpoint);
    }

    protected Response postRequest(Object payload, String endpoint, Map<String, String> headers) {
        return requestSpecification
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(payload)
                .post(endpoint);
    }

    protected Response patchRequest(Object payload, String endpoint, Map<String, String> headers) {
        return requestSpecification
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(payload)
                .patch(endpoint);
    }

    protected Response patchRequest(Object payload, String endpoint) {
        return requestSpecification
                .contentType(ContentType.JSON)
                .body(payload)
                .patch(endpoint);
    }

    protected Response putRequest(Object payload, String endpoint, Map<String, String> headers) {
        return requestSpecification
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(payload)
                .put(endpoint);
    }

    protected Response deleteRequest(String endpoint, Map<String, String> headers) {
        return requestSpecification
                .headers(headers)
                .delete(endpoint);
    }
}