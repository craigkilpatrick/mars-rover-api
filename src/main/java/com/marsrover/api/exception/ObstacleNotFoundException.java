package com.marsrover.api.exception;

public class ObstacleNotFoundException extends RuntimeException {

    public ObstacleNotFoundException(Long id) {
        super("Could not find obstacle " + id);
    }

    public ObstacleNotFoundException(String message) {
        super(message);
    }
}
