package com.marsrover.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid command provided")
public class InvalidCommandException extends RuntimeException {
  private final Long id;

  public InvalidCommandException(Long id, char command) {
    super("Invalid command received: " + command);
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
