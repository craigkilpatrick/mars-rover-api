package com.marsrover.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid rover creation parameters")
public class InvalidRoverCreationParams {
  private String message;
  private String errorType;

  public InvalidRoverCreationParams(String message, String errorType) {
      this.message = message;
      this.errorType = errorType;
  }

  public String getMessage() {
      return message;
  }

  public void setMessage(String message) {
      this.message = message;
  }

  public String getErrorType() {
      return errorType;
  }

  public void setErrorType(String errorType) {
      this.errorType = errorType;
  }
}
