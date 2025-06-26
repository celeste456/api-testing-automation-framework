package com.api.models.responses;

public class GenericResponse {
    private boolean success;
    private int status;
    private String message;

    public GenericResponse() {
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
}
