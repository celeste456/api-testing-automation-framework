package com.api.models.request;

public class UpdateStatusRequest {
    private boolean completed;

    private UpdateStatusRequest(Builder builder) {
        this.completed = builder.completed;
    }

    public static class Builder {
        private boolean completed;

        public Builder completed(boolean completed) {
            this.completed = completed;
            return this;
        }

        public UpdateStatusRequest build() {
            return new UpdateStatusRequest(this);
        }
    }

    public boolean isCompleted() {
        return completed;
    }
}
