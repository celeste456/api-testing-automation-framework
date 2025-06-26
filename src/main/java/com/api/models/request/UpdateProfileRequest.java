package com.api.models.request;

public class UpdateProfileRequest {
    private String name;
    private String phone;
    private String company;

    public UpdateProfileRequest(String name, String phone, String company) {
        this.name = name;
        this.phone = phone;
        this.company = company;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getCompany() { return company; }

    public static class Builder {
        private String name;
        private String phone;
        private String company;

        public Builder Name(String name) {
            this.name = name;
            return this;
        }

        public Builder Phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder Company(String company) {
            this.company = company;
            return this;
        }

        public UpdateProfileRequest Build() {
            return new UpdateProfileRequest(name, phone, company);
        }
    }
}
