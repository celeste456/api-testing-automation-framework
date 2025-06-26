package com.api.models.request;

public class ForgotPasswordRequest {
    private String email;

    public ForgotPasswordRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static class Builder {
        private String email;

        public Builder Email(String email) {
            this.email = email;
            return this;
        }

        public ForgotPasswordRequest Build() {
            return new ForgotPasswordRequest(email);
        }
    }
}
