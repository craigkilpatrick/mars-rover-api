package com.marsrover.api.domain;

import java.util.Objects;

import com.marsrover.api.validation.CharPattern;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Rover {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @Schema(description = "X coordinate position", example = "0", required = true)
  @NotNull(message = "X coordinate must not be null")
  @Min(value = 0, message = "X coordinate must be at least 0")
  @Max(value = 100, message = "X coordinate must be no more than 100")
  private int x;

  @Schema(description = "Y coordinate position", example = "0", required = true)
  @NotNull(message = "Y coordinate must not be null")
  @Min(value = 0, message = "Y coordinate must be at least 0")
  @Max(value = 100, message = "Y coordinate must be no more than 100")
  private int y;

  @Schema(description = "Direction the rover is facing", example = "N", required = true)
  @NotNull(message = "Direction must not be null")
  @CharPattern(regexp = "[NWSE]", message = "Direction must be one of 'N', 'W', 'S', 'E'")
  private char direction;

  /**
   * Default constructor for the Rover class.
   * This constructor is required for instantiation by Interceptor.
   */
  public Rover() {
  }

  /**
   * Constructs a new Rover object with the specified initial position and direction.
   *
   * @param x The initial x-coordinate of the rover.
   * @param y The initial y-coordinate of the rover.
   * @param direction The initial direction of the rover.
   */
  public Rover(int x, int y, char direction) {
  this.x = x;
  this.y = y;
  this.direction = direction;
  }

  /**
   * Gets the ID of the rover.
   *
   * @return The ID of the rover.
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Gets the x-coordinate of the rover.
   *
   * @return The x-coordinate of the rover.
   */
  public int getX() {
    return this.x;
  }

  /**
   * Sets the x-coordinate of the rover.
   *
   * @param x The x-coordinate of the rover.
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Gets the y-coordinate of the rover.
   *
   * @return The y-coordinate of the rover.
   */
  public int getY() {
    return this.y;
  }

  /**
   * Sets the y-coordinate of the rover.
   *
   * @param y The y-coordinate of the rover.
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Gets the direction of the rover.
   *
   * @return The direction of the rover.
   */
  public char getDirection() {
    return this.direction;
  }

  /**
   * Sets the direction of the rover.
   *
   * @param direction The direction of the rover.
   */
  public void setDirection(char direction) {
    this.direction = direction;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param obj the reference object with which to compare
   * @return true if this object is the same as the obj argument; false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Rover)) {
      return false;
    }
    Rover rover = (Rover) obj;
    return Objects.equals(this.id, rover.id) && Objects.equals(this.x, rover.x) && Objects.equals(this.y, rover.y) && Objects.equals(this.direction, rover.direction);
  }

  /**
   * Returns a string representation of the Rover object.
   *
   * @return A string representation of the Rover object in the format "x, y, direction".
   */
  @Override
  public String toString() {
    return x + ", " + y + ", " + direction;
  }
}
