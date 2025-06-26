package com.api.models.request;

public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static class Builder{
        private String email;
        private String password;

        public LoginRequest.Builder Email(String email) {
            this.email = email;
            return this;
        }

        public LoginRequest.Builder Password(String password) {
            this.password = password;
            return this;
        }

        public LoginRequest Build(){
            LoginRequest loginRequest = new LoginRequest(email,password);
            return loginRequest;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
