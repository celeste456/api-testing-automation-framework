package com.api.tests;

import com.api.models.request.UpdateProfileRequest;
import com.api.models.responses.ProfileResponse;
import com.api.services.UsersService;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class ProfileTests {

    private final UsersService userService = new UsersService();

    @Test(description = "Should retrieve profile successfully with valid token")
    public void shouldRetrieveProfileWithValidToken() {
        Response response = userService.profile();
        ProfileResponse profile = response.as(ProfileResponse.class);

        assertEquals(response.statusCode(), 200, "Expected 200 OK");
        assertTrue(profile.isSuccess(), "Success flag should be true");
        assertEquals(profile.getMessage(), "Profile successful", "Expected profile success message");
        assertNotNull(profile.getData().getEmail(), "Email should be present in profile");
    }

    @Test(description = "Should fail to retrieve profile without authentication token")
    public void shouldFailProfileWithoutToken() {
        Response response = userService.profileWithoutAuth();
        String responseBody = response.getBody().asString().toLowerCase();

        assertEquals(response.statusCode(), 401, "Expected 401 Unauthorized");
        assertTrue(responseBody.contains("unauthorized") || responseBody.contains("token"), "Response should mention token/unauthorized");
    }

    @Test(description = "Should update profile successfully with valid data")
    public void shouldUpdateProfileSuccessfully() {
        UpdateProfileRequest request = new UpdateProfileRequest.Builder()
                .Name("Updated Test User")
                .Phone("88888888")
                .Company("Galcom QA")
                .Build();

        Response response = userService.updateProfile(request);
        String responseBody = response.asString().toLowerCase();

        assertEquals(response.statusCode(), 200, "Expected 200 OK");
        assertTrue(responseBody.contains("profile updated successful"), "Response should confirm successful update");
    }

    @Test(description = "Should fail to update profile without authentication token")
    public void shouldFailUpdateProfileWithoutToken() {
        UpdateProfileRequest request = new UpdateProfileRequest.Builder()
                .Name("Unauth User")
                .Phone("8888")
                .Build();

        Response response = userService.updateProfileWithoutAuth(request);

        assertEquals(response.statusCode(), 401, "Expected 401 Unauthorized");

        String actualMessage = response.getBody().asString();
        assertTrue(actualMessage.contains("No authentication token specified in x-auth-token header"),
                "Expected message about missing token");
    }

    @Test(description = "Should return 400 for invalid phone number format")
    public void shouldFailUpdateProfileWithInvalidPhone() {
        UpdateProfileRequest request = new UpdateProfileRequest.Builder()
                .Phone("invalid-phone-%%%")
                .Build();

        Response response = userService.updateProfile(request);
        String responseBody = response.asString().toLowerCase();

        assertEquals(response.statusCode(), 400, "Expected 400 Bad Request");
        assertTrue(responseBody.contains("valid phone") || responseBody.contains("invalid"), "Response should mention invalid phone");
    }

    @Test
    public void logTest() {
        Logger logger = LogManager.getLogger("LogTest");
        logger.info("✅ Esto debería escribirse en consola y archivo");
        assertTrue(true); // Test dummy
    }
}
