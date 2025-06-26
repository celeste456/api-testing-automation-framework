package com.api.services;

import com.api.models.request.ForgotPasswordRequest;
import com.api.models.request.LoginRequest;
import com.api.models.request.RegisterRequest;
import com.api.models.request.UpdateProfileRequest;
import io.restassured.response.Response;

public class UsersService extends BaseService {
    private static final String BASE_PATH = "/users/";

    public Response register(RegisterRequest payload) {
        return postRequest(payload, BASE_PATH + "register");
    }

    public Response login(LoginRequest payload) {
        return postRequest(payload, BASE_PATH + "login");
    }

    public Response forgotPassword(ForgotPasswordRequest payload) {
        return postRequest(payload, BASE_PATH + "forgot-password");
    }

    public Response profile() {
        return getRequest(BASE_PATH + "profile", authHeader());
    }

    public Response profileWithoutAuth() {
        return getRequest(BASE_PATH + "profile");
    }

    public Response updateProfile(UpdateProfileRequest payload) {
        return patchRequest(payload, BASE_PATH + "profile", authHeader());
    }

    public Response updateProfileWithoutAuth(UpdateProfileRequest payload) {
        return patchRequest(payload, BASE_PATH + "profile");
    }

}
