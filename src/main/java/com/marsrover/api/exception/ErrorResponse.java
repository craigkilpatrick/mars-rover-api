package com.marsrover.api.exception;

public class ErrorResponse {
    private String message;
    private Long id;
    private String errorType;

    /**
     * Constructs a new ErrorResponse object with the specified message, ID, and error type.
     *
     * @param message    the error message
     * @param id         the ID associated with the error
     * @param errorType  the type of error
     */
    public ErrorResponse(String message, Long id, String errorType) {
        this.message = message;
        this.id = id;
        this.errorType = errorType;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
