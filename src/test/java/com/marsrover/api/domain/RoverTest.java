package com.marsrover.api.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoverTest {

    @Test
    void constructor_shouldSetFields() {
        Rover rover = new Rover(3, 7, 'E');

        assertEquals(3, rover.getX());
        assertEquals(7, rover.getY());
        assertEquals('E', rover.getDirection());
    }

    @Test
    void setters_shouldUpdateFields() {
        Rover rover = new Rover(0, 0, 'N');
        rover.setX(10);
        rover.setY(20);
        rover.setDirection('S');

        assertEquals(10, rover.getX());
        assertEquals(20, rover.getY());
        assertEquals('S', rover.getDirection());
    }

    @Test
    void equals_shouldReturnTrueForSameObject() {
        Rover rover = new Rover(0, 0, 'N');
        assertEquals(rover, rover);
    }

    @Test
    void equals_shouldReturnFalseForNull() {
        Rover rover = new Rover(0, 0, 'N');
        assertNotEquals(null, rover);
    }

    @Test
    void equals_shouldReturnFalseForDifferentType() {
        Rover rover = new Rover(0, 0, 'N');
        assertNotEquals("not a rover", rover);
    }

    @Test
    void equals_shouldReturnTrueForEqualFields() {
        Rover r1 = new Rover(1, 2, 'E');
        Rover r2 = new Rover(1, 2, 'E');

        assertEquals(r1, r2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentFields() {
        Rover r1 = new Rover(1, 2, 'E');
        Rover r2 = new Rover(1, 2, 'N');

        assertNotEquals(r1, r2);
    }

    @Test
    void hashCode_shouldBeConsistentWithEquals() {
        Rover r1 = new Rover(1, 2, 'E');
        Rover r2 = new Rover(1, 2, 'E');

        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void toString_shouldContainCoordinatesAndDirection() {
        Rover rover = new Rover(3, 7, 'W');
        String result = rover.toString();

        assertTrue(result.contains("3"));
        assertTrue(result.contains("7"));
        assertTrue(result.contains("W"));
    }

    @Test
    void defaultConstructor_shouldCreateRover() {
        Rover rover = new Rover();
        assertNotNull(rover);
    }
}
