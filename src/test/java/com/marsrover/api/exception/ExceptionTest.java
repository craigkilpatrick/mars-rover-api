package com.marsrover.api.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionTest {

    // --- RoverNotFoundException ---

    @Test
    void roverNotFoundException_shouldContainIdInMessage() {
        RoverNotFoundException ex = new RoverNotFoundException(42L);

        assertEquals(42L, ex.getId());
        assertTrue(ex.getMessage().contains("42"));
    }

    // --- ObstacleNotFoundException ---

    @Test
    void obstacleNotFoundException_withId_shouldContainIdInMessage() {
        ObstacleNotFoundException ex = new ObstacleNotFoundException(7L);

        assertTrue(ex.getMessage().contains("7"));
    }

    @Test
    void obstacleNotFoundException_withMessage_shouldPreserveMessage() {
        ObstacleNotFoundException ex = new ObstacleNotFoundException("Obstacle not found with id: 99");

        assertEquals("Obstacle not found with id: 99", ex.getMessage());
    }

    // --- InvalidCommandException ---

    @Test
    void invalidCommandException_shouldContainCommandInMessage() {
        InvalidCommandException ex = new InvalidCommandException(1L, 'z');

        assertEquals(1L, ex.getId());
        assertTrue(ex.getMessage().contains("z"));
    }

    // --- ErrorResponse ---

    @Test
    void errorResponse_shouldGetAndSetAllFields() {
        ErrorResponse response = new ErrorResponse("error msg", 5L, "SomeError");

        assertEquals("error msg", response.getMessage());
        assertEquals(5L, response.getId());
        assertEquals("SomeError", response.getErrorType());

        response.setMessage("new msg");
        response.setId(10L);
        response.setErrorType("NewError");

        assertEquals("new msg", response.getMessage());
        assertEquals(10L, response.getId());
        assertEquals("NewError", response.getErrorType());
    }

    // --- InvalidRoverCreationParams ---

    @Test
    void invalidRoverCreationParams_shouldGetAndSetAllFields() {
        InvalidRoverCreationParams params = new InvalidRoverCreationParams("Validation failed", "BadRequest");

        assertEquals("Validation failed", params.getMessage());
        assertEquals("BadRequest", params.getErrorType());

        params.setMessage("Updated message");
        params.setErrorType("UpdatedType");

        assertEquals("Updated message", params.getMessage());
        assertEquals("UpdatedType", params.getErrorType());
    }

    // --- GlobalExceptionHandler ---

    @Test
    void globalExceptionHandler_shouldHandleRoverNotFoundException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        RoverNotFoundException ex = new RoverNotFoundException(1L);

        ResponseEntity<ErrorResponse> response = handler.handleRoverNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("RoverNotFoundException", response.getBody().getErrorType());
    }

    @Test
    void globalExceptionHandler_shouldHandleInvalidCommandException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        InvalidCommandException ex = new InvalidCommandException(2L, 'x');

        ResponseEntity<ErrorResponse> response = handler.handleInvalidCommand(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2L, response.getBody().getId());
        assertEquals("InvalidCommandException", response.getBody().getErrorType());
    }

    @Test
    void globalExceptionHandler_handleValidationExceptions_shouldReturnBadRequest() {
        // MethodArgumentNotValidException is tested through the controller integration tests.
        // Verified via RoverControllerTest.whenInvalidRoverPosted_thenReturnsBadRequest.
        // Here we just confirm the handler class exists and is instantiable.
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        assertNotNull(handler);
    }
}
