package com.api.models.responses;

public class LoginResponse {
    private boolean success;
    private int status;
    private String message;
    private LoginData data;

    public LoginResponse() {
    }

    public LoginResponse(boolean success, int status, String message, LoginData data) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LoginData getData() {
        return data;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "success=" + success +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class LoginData {
        private String id;
        private String email;
        private String name;
        private String token;

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getToken() {
            return token;
        }

        @Override
        public String toString() {
            return "LoginData{" +
                    "id='" + id + '\'' +
                    ", email='" + email + '\'' +
                    ", name='" + name + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }
}
