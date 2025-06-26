package com.api.models.request;

public class UpdateNoteRequest {
    private String title;
    private String description;
    private boolean completed;
    private String category;

    private UpdateNoteRequest(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.completed = builder.completed;
        this.category = builder.category;
    }

    public static class Builder {
        private String title;
        private String description;
        private boolean completed;
        private String category;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder completed(boolean completed) {
            this.completed = completed;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public UpdateNoteRequest build() {
            return new UpdateNoteRequest(this);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getCategory() {
        return category;
    }
}
