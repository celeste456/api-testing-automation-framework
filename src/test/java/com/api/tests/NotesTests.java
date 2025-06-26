package com.api.tests;

import com.api.models.request.NoteRequest;
import com.api.models.request.UpdateNoteRequest;
import com.api.models.request.UpdateStatusRequest;
import com.api.services.NotesService;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class NotesTests {
    private final NotesService notesService = new NotesService();

    @Test(description = "Should create a note successfully")
    public void shouldCreateNoteSuccessfully() {
        NoteRequest request = new NoteRequest.Builder()
                .Title("Test Note")
                .Description("This is a test note content.")
                .Completed(false)
                .Category("Home")
                .Build();

        Response response = notesService.createNote(request);
        System.out.println(response.asString());
        assertEquals(response.statusCode(), 200, "Expected 200 Created");
        assertTrue(response.asString().toLowerCase().contains("note successfully created"),
                "Expected message 'Note successfully created' in response body");
    }

    @Test(description = "Should fail to create note without title")
    public void shouldFailToCreateNoteWithoutTitle() {
        NoteRequest request = new NoteRequest.Builder()
                .Title("")
                .Description("This note has no title.")
                .Completed(false)
                .Category("Home")
                .Build();

        Response response = notesService.createNote(request);
        assertEquals(response.statusCode(), 400, "Expected 400 Bad Request for missing title");
        assertTrue(response.asString().toLowerCase().contains("title"), "Error message should mention title");
    }

    @Test(description = "Should fail to create note without authentication token")
    public void shouldFailToCreateNoteWithoutToken() {
        NoteRequest request = new NoteRequest.Builder()
                .Title("Unauthenticated note")
                .Description("Trying to create note without token.")
                .Completed(false)
                .Category("Home")
                .Build();

        Response response = notesService.createNoteWithoutAuth(request);
        assertEquals(response.statusCode(), 401, "Expected 401 Unauthorized");
        assertTrue(response.asString().toLowerCase().contains("no authentication token"), "Response should indicate missing token");
    }

    @Test(description = "Should retrieve note by ID successfully")
    public void shouldRetrieveNoteById() {
        NoteRequest note = new NoteRequest.Builder()
                .Title("Test Note")
                .Description("Retrieve by ID")
                .Completed(false)
                .Category("Home")
                .Build();

        Response createResponse = notesService.createNote(note);
        System.out.println("CREATE RESPONSE: " + createResponse.asString());
        assertEquals(createResponse.statusCode(), 200);
        String noteId = createResponse.jsonPath().getString("data.id");
        assertNotNull(noteId, "Note ID should not be null");

        Response getResponse = notesService.getNoteById(noteId);

        assertEquals(getResponse.statusCode(), 200, "Expected 200 OK on retrieval");
        assertTrue(getResponse.asString().contains("Test Note"), "Title should match");
    }

    @Test(description = "Should return 404 when retrieving invalid note ID")
    public void shouldReturn404ForInvalidNoteId() {
        Response response = notesService.getNoteById("000000000000000000000000");

        System.out.println("RESPONSE: " + response.asString());  // útil para debugging

        assertEquals(response.statusCode(), 404, "Expected 404 Not Found");

        String actualMessage = response.jsonPath().getString("message");
        assertEquals(actualMessage, "No note was found with the provided ID, Maybe it was deleted",
                "Expected specific message for not found note");
    }

    @Test(description = "Update an existing note successfully with all fields")
    public void updateNoteSuccessfully() {
        NoteRequest originalNote = new NoteRequest.Builder()
                .Title("Original Title")
                .Description("Original Content")
                .Completed(false)
                .Category("Home")
                .Build();

        Response createResponse = notesService.createNote(originalNote);
        assertEquals(createResponse.statusCode(), 200, "Note creation should return 200 Created");

        String noteId = createResponse.jsonPath().getString("data.id");
        assertNotNull(noteId, "Note ID should not be null");

        UpdateNoteRequest updatedNote = new UpdateNoteRequest.Builder()
                .title("Updated Title")
                .description("Updated Description")
                .completed(true)
                .category("Home")
                .build();

        Response updateResponse = notesService.updateNote(noteId, updatedNote);

        assertEquals(updateResponse.statusCode(), 200, "Update should return 200 OK");
        assertTrue(updateResponse.asString().contains("Updated Title"), "Response should contain updated title");
        assertTrue(updateResponse.asString().contains("Updated Description"), "Response should contain updated description");
        assertTrue(updateResponse.asString().contains("true"), "Completed should be true in the response");
    }

    @Test(description = "Should return 404 when updating invalid note ID")
    public void shouldFailToUpdateInvalidNoteId() {
        UpdateNoteRequest update = new UpdateNoteRequest.Builder()
                .title("Any title")
                .description("Any content")
                .category("Home")
                .completed(false)
                .build();

        String fakeId = "000000000000000000000000";

        Response response = notesService.updateNote(fakeId, update);
        System.out.println("RESPONSE: " + response.asString());

        assertEquals(response.statusCode(), 404, "Expected 404 Not Found");
        assertTrue(response.asString().toLowerCase().contains("no note was found"), "Error message should mention note not found");
    }

    @Test(description = "Patch note completed status successfully")
    public void patchNoteCompletedSuccessfully() {
        NoteRequest createRequest = new NoteRequest.Builder()
                .Title("Patch Test Note")
                .Description("Patchable content")
                .Completed(false)
                .Category("Home")
                .Build();

        Response createResponse = notesService.createNote(createRequest);
        assertEquals(createResponse.statusCode(), 200, "Note creation should succeed");

        String noteId = createResponse.jsonPath().getString("data.id");
        assertNotNull(noteId, "Note ID should not be null");

        UpdateStatusRequest patchRequest = new UpdateStatusRequest.Builder()
                .completed(true)
                .build();

        Response patchResponse = notesService.updateNoteStatus(noteId, patchRequest);
        assertEquals(patchResponse.statusCode(), 200, "Patch should return 200 OK");
        assertTrue(patchResponse.asString().toLowerCase().contains("updated"), "Response should indicate update");

        Response getResponse = notesService.getNoteById(noteId);
        assertTrue(getResponse.jsonPath().getBoolean("data.completed"), "Note should be marked as completed");
    }

    @Test(description = "Should delete a note successfully")
    public void shouldDeleteNoteSuccessfully() {
        NoteRequest request = new NoteRequest.Builder()
                .Title("To be deleted")
                .Description("Delete this")
                .Category("Work")
                .Build();

        Response create = notesService.createNote(request);
        String noteId = create.jsonPath().getString("data.id");
        Response response = notesService.deleteNote(noteId);
        assertTrue(response.statusCode() == 200 || response.statusCode() == 204, "Expected 200 or 204 on delete");
    }

    @Test(description = "Should return 404 when deleting note with invalid ID")
    public void shouldFailToDeleteInvalidNote() {
        Response response = notesService.deleteNote("000000000000000000000000");
        System.out.println(response.toString());
        assertEquals(response.statusCode(), 404, "Expected 404 Not Found for invalid delete");
        assertEquals(response.jsonPath().getString("message"), "No note was found with the provided ID, Maybe it was deleted");
    }

    @Test(description = "Should retrieve notes for the logged-in user")
    public void shouldRetrieveUserNotes() {
        Response response = notesService.getAllNotes();
        assertEquals(response.statusCode(), 200, "Expected 200 OK");

        List<?> notes = response.jsonPath().getList("data");
        assertNotNull(notes, "Notes should not be null");

        if (!notes.isEmpty()) {
            String userId = response.jsonPath().getString("data[0].user_id");
            System.out.println("User ID from note: " + userId);
        }
    }

}
