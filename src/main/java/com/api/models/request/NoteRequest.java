package com.api.models.request;

public class NoteRequest {
    private String title;
    private String description;
    private Boolean completed;
    private String category;

    public NoteRequest(String title, String description, Boolean completed, String category) {
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public String getCategory() {
        return category;
    }

    public static class Builder {
        private String title;
        private String description;
        private Boolean completed;
        private String category;


        public Builder Title(String title) {
            this.title = title;
            return this;
        }

        public Builder Description(String description) {
            this.description = description;
            return this;
        }

        public Builder Completed(Boolean completed) {
            this.completed = completed;
            return this;
        }

        public Builder Category(String category) {
            this.category = category;
            return this;
        }

        public NoteRequest Build() {
            return new NoteRequest(title, description, completed, category);
        }
    }
}
