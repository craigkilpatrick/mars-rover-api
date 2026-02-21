package com.marsrover.api.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.marsrover.api.domain.Rover;
import com.marsrover.api.exception.InvalidCommandException;
import com.marsrover.api.repository.RoverRepository;
import com.marsrover.api.repository.ObstacleRepository;

@Service
public class RoverService {

  private final RoverRepository roverRepository;
  private final ObstacleRepository obstacleRepository;

  private int planetSize = 100;

  public RoverService(RoverRepository roverRepository, ObstacleRepository obstacleRepository) {
    this.roverRepository = roverRepository;
    this.obstacleRepository = obstacleRepository;
  }

  @Value("${mars.planet.size:100}")
  public void setPlanetSize(int planetSize) {
    this.planetSize = planetSize;
  }

  /**
   * Result class for command processing that includes success status and optional message.
   */
  public static class CommandResult {
    private final boolean successful;
    private final String message;

    public CommandResult(boolean successful, String message) {
      this.successful = successful;
      this.message = message;
    }

    public CommandResult() {
      this.successful = true;
      this.message = null;
    }

    public boolean isSuccessful() {
      return successful;
    }

    public String getMessage() {
      return message;
    }
  }

  /**
   * Processes an array of commands for a given Rover.
   * Each command is executed sequentially, updating the Rover's state after each command.
   * If an obstacle is detected, processing stops and returns information about the obstacle.
   *
   * @param rover    The Rover object to execute the commands on.
   * @param commands An array of characters representing the commands to be executed.
   * @return CommandResult indicating success or failure with obstacle information
   */
  public CommandResult processCommands(Rover rover, char[] commands) {
    Set<String> obstacles = buildObstacleSet();
    for (char command : commands) {
      switch (command) {
        case 'f':
          if (!moveForward(rover, obstacles)) {
            int obstacleX = getForwardX(rover);
            int obstacleY = getForwardY(rover);
            roverRepository.save(rover);
            return new CommandResult(false, "Obstacle detected at (" + obstacleX + ", " + obstacleY + ")");
          }
          break;
        case 'b':
          if (!moveBackward(rover, obstacles)) {
            int obstacleX = getBackwardX(rover);
            int obstacleY = getBackwardY(rover);
            roverRepository.save(rover);
            return new CommandResult(false, "Obstacle detected at (" + obstacleX + ", " + obstacleY + ")");
          }
          break;
        case 'l':
          turnLeft(rover);
          break;
        case 'r':
          turnRight(rover);
          break;
        default:
          roverRepository.save(rover);
          throw new InvalidCommandException(rover.getId(), command);
      }
    }
    roverRepository.save(rover);
    return new CommandResult();
  }

  private Set<String> buildObstacleSet() {
    return obstacleRepository.findAll().stream()
        .map(o -> o.getX() + "," + o.getY())
        .collect(Collectors.toSet());
  }

  private boolean isObstacleAt(Set<String> obstacles, int x, int y) {
    return obstacles.contains(x + "," + y);
  }

  /**
   * Turns the rover 90 degrees to the left.
   */
  private void turnLeft(Rover rover) {
    switch (rover.getDirection()) {
      case 'N':
        rover.setDirection('W');
        break;
      case 'W':
        rover.setDirection('S');
        break;
      case 'S':
        rover.setDirection('E');
        break;
      case 'E':
        rover.setDirection('N');
        break;
    }
  }

  /**
   * Turns the rover 90 degrees to the right.
   */
  private void turnRight(Rover rover) {
    switch (rover.getDirection()) {
      case 'N':
        rover.setDirection('E');
        break;
      case 'E':
        rover.setDirection('S');
        break;
      case 'S':
        rover.setDirection('W');
        break;
      case 'W':
        rover.setDirection('N');
        break;
    }
  }

  /**
   * Calculates the X coordinate in front of the rover based on its current position and direction.
   */
  private int getForwardX(Rover rover) {
    int newX = rover.getX();
    switch (rover.getDirection()) {
      case 'E':
        newX = (newX + 1) % planetSize;
        break;
      case 'W':
        newX = (newX - 1 + planetSize) % planetSize;
        break;
    }
    return newX;
  }

  /**
   * Calculates the Y coordinate in front of the rover based on its current position and direction.
   */
  private int getForwardY(Rover rover) {
    int newY = rover.getY();
    switch (rover.getDirection()) {
      case 'N':
        newY = (newY + 1) % planetSize;
        break;
      case 'S':
        newY = (newY - 1 + planetSize) % planetSize;
        break;
    }
    return newY;
  }

  /**
   * Calculates the X coordinate behind the rover based on its current position and direction.
   */
  private int getBackwardX(Rover rover) {
    int newX = rover.getX();
    switch (rover.getDirection()) {
      case 'E':
        newX = (newX - 1 + planetSize) % planetSize;
        break;
      case 'W':
        newX = (newX + 1) % planetSize;
        break;
    }
    return newX;
  }

  /**
   * Calculates the Y coordinate behind the rover based on its current position and direction.
   */
  private int getBackwardY(Rover rover) {
    int newY = rover.getY();
    switch (rover.getDirection()) {
      case 'N':
        newY = (newY - 1 + planetSize) % planetSize;
        break;
      case 'S':
        newY = (newY + 1) % planetSize;
        break;
    }
    return newY;
  }

  /**
   * Moves the rover forward based on its current direction.
   * @return true if the move was successful, false if an obstacle was detected
   */
  private boolean moveForward(Rover rover, Set<String> obstacles) {
    int newX = getForwardX(rover);
    int newY = getForwardY(rover);

    if (isObstacleAt(obstacles, newX, newY)) {
      return false;
    }

    rover.setX(newX);
    rover.setY(newY);
    return true;
  }

  /**
   * Moves the rover backward based on its current direction.
   * @return true if the move was successful, false if an obstacle was detected
   */
  private boolean moveBackward(Rover rover, Set<String> obstacles) {
    int newX = getBackwardX(rover);
    int newY = getBackwardY(rover);

    if (isObstacleAt(obstacles, newX, newY)) {
      return false;
    }

    rover.setX(newX);
    rover.setY(newY);
    return true;
  }
}
