package com.api.models.responses;

public class ProfileResponse {
    private boolean success;
    private int status;
    private String message;
    private ProfileData data;

    public boolean isSuccess() { return success; }
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public ProfileData getData() { return data; }

    public static class ProfileData {
        private String id;
        private String name;
        private String email;
        private String phone;
        private String company;

        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public String getCompany() { return company; }
    }
}
