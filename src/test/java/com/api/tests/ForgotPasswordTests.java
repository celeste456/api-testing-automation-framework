package com.api.tests;

import com.api.models.request.ForgotPasswordRequest;
import com.api.models.responses.GenericResponse;
import com.api.services.UsersService;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ForgotPasswordTests {
    private final UsersService userService = new UsersService();

    @Test(description = "Should send password reset link to a registered email")
    public void shouldSendResetLinkToRegisteredEmail() {
        ForgotPasswordRequest request = new ForgotPasswordRequest.Builder()
                .Email("celeste_api_test@gmail.com")
                .Build();

        Response response = userService.forgotPassword(request);
        GenericResponse body = response.as(GenericResponse.class);

        assertEquals(response.statusCode(), 200, "Expected 200 OK for registered email");
        assertTrue(body.isSuccess(), "Expected success field to be true");
        assertTrue(body.getMessage().toLowerCase().contains("success"),
                "Expected success message confirming reset link sent");
    }

    @Test(description = "Should fail to send reset link to unregistered email")
    public void shouldFailForUnregisteredEmail() {
        ForgotPasswordRequest request = new ForgotPasswordRequest.Builder()
                .Email("notfound_user_testcase@email.com")
                .Build();

        Response response = userService.forgotPassword(request);
        GenericResponse body = response.as(GenericResponse.class);

        assertEquals(response.statusCode(), 401, "Expected 401 Unauthorized for unregistered email");
        assertFalse(body.isSuccess(), "Expected success field to be false");
        assertEquals(body.getMessage(), "No account found with the given email address",
                "Expected exact error message for unregistered email");
    }

    @Test(description = "Should fail to send reset link with empty email field")
    public void shouldFailForEmptyEmail() {
        ForgotPasswordRequest request = new ForgotPasswordRequest.Builder()
                .Email("")
                .Build();

        Response response = userService.forgotPassword(request);
        GenericResponse body = response.as(GenericResponse.class);

        assertEquals(response.statusCode(), 400, "Expected 400 Bad Request for empty email");
        assertFalse(body.isSuccess(), "Expected success field to be false");
        assertTrue(body.getMessage().toLowerCase().contains("email") ||
                        body.getMessage().toLowerCase().contains("invalid"),
                "Expected message indicating invalid or missing email");
    }
}
