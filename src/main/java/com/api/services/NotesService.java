package com.api.services;

import com.api.models.request.NoteRequest;
import com.api.models.request.UpdateNoteRequest;
import com.api.models.request.UpdateStatusRequest;
import io.restassured.response.Response;

public class NotesService extends BaseService{

    private static final String BASE_PATH = "/notes";

    public Response createNote(NoteRequest request) {
        return postRequest(request, BASE_PATH, authHeader());
    }

    public Response createNoteWithoutAuth(NoteRequest request) {
        return postRequest(request, BASE_PATH);
    }

    public Response getAllNotes() {
        return getRequest(BASE_PATH, authHeader());
    }

    public Response getNoteById(String noteId) {
        return getRequest(BASE_PATH + "/" + noteId, authHeader());
    }

    public Response updateNote(String noteId, UpdateNoteRequest request) {
        return putRequest(request, BASE_PATH + "/" + noteId, authHeader());
    }

    public Response updateNoteStatus(String noteId, UpdateStatusRequest request) {
        return patchRequest(request, BASE_PATH + "/" + noteId, authHeader());
    }

    public Response deleteNote(String noteId) {
        return deleteRequest(BASE_PATH + "/" + noteId, authHeader());
    }
}
