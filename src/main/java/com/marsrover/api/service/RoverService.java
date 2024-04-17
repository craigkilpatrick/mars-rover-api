package com.marsrover.api.service;

import org.springframework.stereotype.Service;

import com.marsrover.api.domain.Rover;
import com.marsrover.api.exception.InvalidCommandException;
import com.marsrover.api.repository.RoverRepository;

@Service
public class RoverService {

  private final RoverRepository roverRepository;

  public RoverService(RoverRepository roverRepository) {
    this.roverRepository = roverRepository;
  }

  private final int planetSize = 100; // Assuming a square grid for simplicity

  /**
   * Processes an array of commands for a given Rover.
   * Each command is executed sequentially, updating the Rover's state after each command.
   *
   * @param rover    The Rover object to execute the commands on.
   * @param commands An array of characters representing the commands to be executed.
   */
  public void processCommands(Rover rover, char[] commands) {
    for (char command : commands) {
      switch (command) {
        case 'f':
          moveForward(rover);
          break;
        case 'b':
          moveBackward(rover);
          break;
        case 'l':
          turnLeft(rover);
          break;
        case 'r':
          turnRight(rover);
          break;
        default:
          rover.toString();
          throw new InvalidCommandException(rover.getId(),command);
      }
      roverRepository.save(rover); // Save the updated rover state after each command
    }
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
   * Moves the rover forward based on its current direction.
   */
  private void moveForward(Rover rover) {
    int newX = rover.getX();
    int newY = rover.getY();
    switch (rover.getDirection()) {
      case 'N':
        newY = (newY + 1) % planetSize;
        break;
      case 'E':
        newX = (newX + 1) % planetSize;
        break;
      case 'S':
        newY = (newY - 1 + planetSize) % planetSize;
        break;
      case 'W':
        newX = (newX - 1 + planetSize) % planetSize;
        break;
    }

    // Update rover's position
    rover.setX(newX);
    rover.setY(newY);
  }

  /**
   * Moves the rover backward based on its current direction.
   */
  private void moveBackward(Rover rover) {
    int newX = rover.getX();
    int newY = rover.getY();
    switch (rover.getDirection()) {
      case 'N':
      newY = (newY - 1 + planetSize) % planetSize;
      break;
      case 'E':
      newX = (newX - 1 + planetSize) % planetSize;
      break;
      case 'S':
      newY = (newY + 1) % planetSize;
      break;
      case 'W':
      newX = (newX + 1) % planetSize;
      break;
    }

    // Update rover's position
    rover.setX(newX);
    rover.setY(newY);
  }
}
