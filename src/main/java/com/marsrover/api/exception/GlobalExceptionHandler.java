package com.marsrover.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RoverNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleRoverNotFound(RoverNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(
			ex.getMessage(),
			ex.getId(),
			RoverNotFoundException.class.getSimpleName()
		);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(InvalidCommandException.class)
	public ResponseEntity<ErrorResponse> handleInvalidCommand(InvalidCommandException ex) {
		ErrorResponse errorResponse = new ErrorResponse(
			ex.getMessage(),
			ex.getId(),
			InvalidCommandException.class.getSimpleName()
		);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
			.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
			.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	// @ExceptionHandler(MethodArgumentNotValidException.class)
	// public ResponseEntity<InvalidRoverCreationParams> handleMethodArgumentNotValidException(
	// 				MethodArgumentNotValidException ex, WebRequest request) {

	// 	String message = "Validation failed: " + ex.getBindingResult().getFieldError().getDefaultMessage();
	// 	String errorType = ex.getClass().getSimpleName();

	// 	InvalidRoverCreationParams errors = new InvalidRoverCreationParams(message, errorType);
	// 	return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	// }
}
