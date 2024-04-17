package com.marsrover.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Rover not found")
public class RoverNotFoundException extends RuntimeException {
	private final Long id;

	public RoverNotFoundException(Long id) {
		super("Could not find rover " + id);
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
