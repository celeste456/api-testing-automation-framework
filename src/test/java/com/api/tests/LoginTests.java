package com.api.tests;

import com.api.models.request.LoginRequest;
import com.api.models.responses.LoginResponse;
import com.api.services.UsersService;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class LoginTests{

    private final UsersService userService = new UsersService();

    @Test(description = "Should login successfully with valid credentials")
    public void shouldLoginWithValidCredentials() {
        //Given
        LoginRequest request = new LoginRequest.Builder()
                .Email("celeste_api_test@gmail.com")
                .Password("Celeste#12")
                .Build();

        // When
        Response response = userService.login(request);
        LoginResponse loginResponse = response.as(LoginResponse.class);

        // Then
        assertEquals(response.statusCode(), 200, "Expected 200 OK status");
        assertTrue(loginResponse.isSuccess(), "Login should be successful");
        assertNotNull(loginResponse.getData().getToken(), "Token should not be null");
    }

    @Test(description = "Should fail to login with invalid password")
    public void shouldFailToLoginWithInvalidPassword() {
        LoginRequest request = new LoginRequest.Builder()
                .Email("celeste_api_test@gmail.com")
                .Password("wrongPassword")
                .Build();

        Response response = userService.login(request);
        LoginResponse loginResponse = response.as(LoginResponse.class);

        assertEquals(response.statusCode(), 401, "Expected 401 Unauthorized status");
        assertFalse(loginResponse.isSuccess(), "Login should fail");
        assertTrue(loginResponse.getMessage().toLowerCase().contains("incorrect email address or password"), "Message should indicate incorrect credentials"
        );
    }

    @Test(description = "Should fail to login with unregistered email")
    public void shouldFailToLoginWithUnregisteredEmail() {
        LoginRequest request = new LoginRequest.Builder()
                .Email("nonexistent@test.com")
                .Password("SomePassword#123")
                .Build();

        Response response = userService.login(request);
        LoginResponse loginResponse = response.as(LoginResponse.class);

        assertEquals(response.statusCode(), 401, "Expected 401 Unauthorized status");
        assertFalse(loginResponse.isSuccess(), "Login should fail");
        assertTrue(loginResponse.getMessage().toLowerCase().contains("incorrect email address or password"), "Message should indicate incorrect credentials");
    }

    @Test(description = "Should return 400 when login fields are blank")
    public void shouldReturn400WhenFieldsAreBlank() {
        LoginRequest request = new LoginRequest.Builder()
                .Email("")
                .Password("")
                .Build();

        Response response = userService.login(request);
        LoginResponse loginResponse = response.as(LoginResponse.class);

        assertEquals(response.statusCode(), 400, "Expected 400 Bad Request");
        assertEquals(loginResponse.getMessage(), "A valid email address is required", "Expected message to mention email validation");
    }

    @Test(description = "Should login regardless of email casing",groups = {"knownBug"})
    public void shouldLoginWithEmailCaseInsensitive() {
        LoginRequest request = new LoginRequest.Builder()
                .Email("CELESTE_API_TEST@GMAIL.COM")
                .Password("Celeste#12")
                .Build();

        Response response = userService.login(request);
        LoginResponse loginResponse = response.as(LoginResponse.class);

        assertEquals(response.statusCode(), 200, "Expected 200 OK for case-insensitive email");
        assertTrue(loginResponse.isSuccess(), "Login should be successful");
        assertNotNull(loginResponse.getData().getToken(), "Token should be present");
    }
}
