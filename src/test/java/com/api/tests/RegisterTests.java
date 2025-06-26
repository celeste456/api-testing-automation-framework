package com.api.tests;

import com.api.models.request.RegisterRequest;
import com.api.models.responses.RegisterResponse;
import com.api.services.UsersService;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class RegisterTests {

    private final UsersService userService = new UsersService();
    private static final String STRONG_PASSWORD = "Celeste#12";
    private static final String EXISTING_EMAIL = "celeste123@gmail.com";

    @Test(description = "Should register successfully with valid data")
    public void shouldRegisterSuccessfullyWithValidData() {
        RegisterRequest registerRequest = new RegisterRequest.Builder()
                .Name("Celeste")
                .Email("celeste_api_test@gmail.com")
                .Password(STRONG_PASSWORD)
                .Build();

        Response response = userService.register(registerRequest);
        RegisterResponse registerResponse = response.as(RegisterResponse.class);

        assertEquals(response.statusCode(), 201, "Expected 201 Created on successful registration");
        assertTrue(registerResponse.isSuccess(), "Expected success to be true");
        assertEquals(registerResponse.getMessage(), "User account created successfully");
        assertEquals(registerResponse.getData().getEmail(), "celeste_api_test@gmail.com");
    }

    @Test(description = "Should return 409 when registering with existing email")
    public void shouldFailToRegisterWithExistingEmail() {
        RegisterRequest registerRequest = new RegisterRequest.Builder()
                .Name("Celeste")
                .Email(EXISTING_EMAIL)
                .Password(STRONG_PASSWORD)
                .Build();

        Response response = userService.register(registerRequest);
        RegisterResponse registerResponse = response.as(RegisterResponse.class);

        assertEquals(response.statusCode(), 409, "Expected 409 Conflict when email is already registered");
        assertEquals(registerResponse.getMessage(), "An account already exists with the same email address");
    }

    @Test(description = "Should return 400 when registering with blank fields")
    public void shouldFailToRegisterWithBlankFields() {
        RegisterRequest registerRequest = new RegisterRequest.Builder().Build();

        Response response = userService.register(registerRequest);
        RegisterResponse registerResponse = response.as(RegisterResponse.class);

        assertEquals(response.statusCode(), 400, "Expected 400 Bad Request for empty fields");
        assertEquals(registerResponse.getMessage(), "User name must be between 4 and 30 characters");
    }

    @Test(description = "Should register successfully when name contains special characters")
    public void shouldRegisterWithSpecialCharactersInName() {
        RegisterRequest registerRequest = new RegisterRequest.Builder()
                .Name("Andrés")
                .Email("And123@gmail.com")
                .Password("And#12")
                .Build();

        Response response = userService.register(registerRequest);
        RegisterResponse registerResponse = response.as(RegisterResponse.class);

        assertEquals(response.statusCode(), 201, "Expected 201 Created with special characters in name");
        assertTrue(registerResponse.isSuccess(), "Expected success to be true");
        assertEquals(registerResponse.getMessage(), "User account created successfully");
    }

    @Test(description = "Should not allow registration with numeric-only name")
    public void shouldFailToRegisterWithNumericOnlyName() {
        RegisterRequest request = new RegisterRequest.Builder()
                .Name("123456")
                .Email("invalidname@test.com")
                .Password("StrongPass123!")
                .Build();

        Response response = userService.register(request);
        assertNotEquals(response.statusCode(), 201, "Should not allow numeric-only names like '123456'");
    }

    @Test(description = "Should fail registration with weak password")
    public void shouldFailToRegisterWithWeakPassword() {
        RegisterRequest request = new RegisterRequest.Builder()
                .Name("Luis")
                .Email("Luis@test.com")
                .Password("1234")
                .Build();

        Response response = userService.register(request);
        RegisterResponse registerResponse = response.as(RegisterResponse.class);

        assertEquals(response.statusCode(), 400, "Expected 400 Bad Request for weak password");
        assertEquals(registerResponse.getMessage(), "Password must be between 6 and 30 characters");
    }

    @Test(description = "Should not break API with SQL injection in name")
    public void shouldNotBreakApiWithSqlInjection() {
        RegisterRequest request = new RegisterRequest.Builder()
                .Name("'; DROP TABLE users; --")
                .Email("sqlinjection@test.com")
                .Password("ValidPass#123")
                .Build();

        Response response = userService.register(request);
        assertNotEquals(response.statusCode(), 500, "API should not break on SQL injection");
        assertFalse(response.body().asString().toLowerCase().contains("sql"), "Response should not expose SQL details");
    }
}