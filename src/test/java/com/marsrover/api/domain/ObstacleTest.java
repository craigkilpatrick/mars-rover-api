package com.marsrover.api.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    @Test
    void constructor_shouldSetFields() {
        Obstacle obstacle = new Obstacle(10, 20);

        assertEquals(10, obstacle.getX());
        assertEquals(20, obstacle.getY());
    }

    @Test
    void setters_shouldUpdateFields() {
        Obstacle obstacle = new Obstacle(0, 0);
        obstacle.setX(15);
        obstacle.setY(25);

        assertEquals(15, obstacle.getX());
        assertEquals(25, obstacle.getY());
    }

    @Test
    void equals_shouldReturnTrueForSameObject() {
        Obstacle obstacle = new Obstacle(5, 5);
        assertEquals(obstacle, obstacle);
    }

    @Test
    void equals_shouldReturnFalseForNull() {
        Obstacle obstacle = new Obstacle(5, 5);
        assertNotEquals(null, obstacle);
    }

    @Test
    void equals_shouldReturnFalseForDifferentType() {
        Obstacle obstacle = new Obstacle(5, 5);
        assertNotEquals("not an obstacle", obstacle);
    }

    @Test
    void equals_shouldReturnTrueForEqualFields() {
        Obstacle o1 = new Obstacle(5, 10);
        Obstacle o2 = new Obstacle(5, 10);

        assertEquals(o1, o2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentFields() {
        Obstacle o1 = new Obstacle(5, 10);
        Obstacle o2 = new Obstacle(5, 11);

        assertNotEquals(o1, o2);
    }

    @Test
    void hashCode_shouldBeConsistentWithEquals() {
        Obstacle o1 = new Obstacle(5, 10);
        Obstacle o2 = new Obstacle(5, 10);

        assertEquals(o1.hashCode(), o2.hashCode());
    }

    @Test
    void toString_shouldContainCoordinates() {
        Obstacle obstacle = new Obstacle(7, 9);
        String result = obstacle.toString();

        assertTrue(result.contains("7"));
        assertTrue(result.contains("9"));
    }

    @Test
    void defaultConstructor_shouldCreateObstacle() {
        Obstacle obstacle = new Obstacle();
        assertNotNull(obstacle);
    }
}
