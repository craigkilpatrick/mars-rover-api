package com.marsrover.api.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Represents an obstacle on the Mars surface.
 * Obstacles prevent rovers from moving to the occupied position.
 */
@Entity
public class Obstacle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "X coordinate must not be null")
    @Min(value = 0, message = "X coordinate must be at least 0")
    @Max(value = 99, message = "X coordinate must be no more than 99")
    private int x;

    @NotNull(message = "Y coordinate must not be null")
    @Min(value = 0, message = "Y coordinate must be at least 0")
    @Max(value = 99, message = "Y coordinate must be no more than 99")
    private int y;

    /**
     * Default constructor for the Obstacle class.
     * Required for JPA.
     */
    public Obstacle() {
    }

    /**
     * Constructs a new Obstacle at the specified coordinates.
     *
     * @param x The x-coordinate of the obstacle.
     * @param y The y-coordinate of the obstacle.
     */
    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the ID of the obstacle.
     *
     * @return The ID of the obstacle.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the x-coordinate of the obstacle.
     *
     * @return The x-coordinate of the obstacle.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the obstacle.
     *
     * @param x The x-coordinate of the obstacle.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the obstacle.
     *
     * @return The y-coordinate of the obstacle.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the obstacle.
     *
     * @param y The y-coordinate of the obstacle.
     */
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obstacle obstacle = (Obstacle) o;
        return x == obstacle.x && y == obstacle.y && Objects.equals(id, obstacle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y);
    }

    @Override
    public String toString() {
        return "Obstacle{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
