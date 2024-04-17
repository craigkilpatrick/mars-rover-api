package com.marsrover.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.marsrover.api.domain.Rover;
import com.marsrover.api.exception.InvalidCommandException;
import com.marsrover.api.repository.RoverRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RoverServiceTest {

	@Mock
	private RoverRepository roverRepository;

	private RoverService roverService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		roverService = new RoverService(roverRepository);
	}

	@Test
	void processCommands_shouldMoveForwardFacingNorth() {
		// Arrange
		Rover rover = new Rover(0, 0, 'N');
		char[] commands = {'f', 'f', 'f'};

		// Act
		roverService.processCommands(rover, commands);

		// Assert
		assertEquals(0, rover.getX());
		assertEquals(3, rover.getY());
		assertEquals('N', rover.getDirection());
		verify(roverRepository, times(3)).save(rover);
	}

	@Test
	void processCommands_shouldMoveForwardFacingEast() {
		// Arrange
		Rover rover = new Rover(0, 0, 'E');
		char[] commands = {'f', 'f', 'f'};

		// Act
		roverService.processCommands(rover, commands);

		// Assert
		assertEquals(3, rover.getX());
		assertEquals(0, rover.getY());
		assertEquals('E', rover.getDirection());
		verify(roverRepository, times(3)).save(rover);
	}

	@Test
	void processCommands_shouldMoveForwardFacingSouth() {
		// Arrange
		Rover rover = new Rover(0, 0, 'S');
		char[] commands = {'f', 'f', 'f'};

		// Act
		roverService.processCommands(rover, commands);

		// Assert
		assertEquals(0, rover.getX());
		assertEquals(97, rover.getY());
		assertEquals('S', rover.getDirection());
		verify(roverRepository, times(3)).save(rover);
	}

	@Test
	void processCommands_shouldMoveForwardFacingWest() {
		// Arrange
		Rover rover = new Rover(0, 0, 'W');
		char[] commands = {'f', 'f', 'f'};

		// Act
		roverService.processCommands(rover, commands);

		// Assert
		assertEquals(97, rover.getX());
		assertEquals(0, rover.getY());
		assertEquals('W', rover.getDirection());
		verify(roverRepository, times(3)).save(rover);
	}

	@Test
	void processCommands_shouldMoveBackwardFacingNorth() {
		// Arrange
		Rover rover = new Rover(0, 0, 'N');
		char[] commands = {'b', 'b', 'b'};

		// Act
		roverService.processCommands(rover, commands);

		// Assert
		assertEquals(0, rover.getX());
		assertEquals(97, rover.getY());
		assertEquals('N', rover.getDirection());
		verify(roverRepository, times(3)).save(rover);
	}

	@Test
	void processCommands_shouldMoveBackwardFacingEast() {
		// Arrange
		Rover rover = new Rover(0, 0, 'E');
		char[] commands = {'b', 'b', 'b'};

		// Act
		roverService.processCommands(rover, commands);

		// Assert
		assertEquals(97, rover.getX());
		assertEquals(0, rover.getY());
		assertEquals('E', rover.getDirection());
		verify(roverRepository, times(3)).save(rover);
	}

	@Test
	void processCommands_shouldMoveBackwardFacingSouth() {
		// Arrange
		Rover rover = new Rover(0, 0, 'S');
		char[] commands = {'b', 'b', 'b'};

		// Act
		roverService.processCommands(rover, commands);

		// Assert
		assertEquals(0, rover.getX());
		assertEquals(3, rover.getY());
		assertEquals('S', rover.getDirection());
		verify(roverRepository, times(3)).save(rover);
	}

	@Test
	void processCommands_shouldMoveBackwardFacingWest() {
		// Arrange
		Rover rover = new Rover(0, 0, 'W');
		char[] commands = {'b', 'b', 'b'};

		// Act
		roverService.processCommands(rover, commands);

		// Assert
		assertEquals(3, rover.getX());
		assertEquals(0, rover.getY());
		assertEquals('W', rover.getDirection());
		verify(roverRepository, times(3)).save(rover);
	}

	@Test
	void processCommands_shouldTurnLeft() {
		// Arrange
		Rover rover = new Rover(0, 0, 'N');
		char[] commands = {'l', 'l', 'l', 'l'};

		// Act
		roverService.processCommands(rover, commands);

		// Assert
		assertEquals(0, rover.getX());
		assertEquals(0, rover.getY());
		assertEquals('N', rover.getDirection());
		verify(roverRepository, times(4)).save(rover);
	}

	@Test
	void processCommands_shouldTurnRight() {
		// Arrange
		Rover rover = new Rover(0, 0, 'N');
		char[] commands = {'r', 'r', 'r', 'r'};

		// Act
		roverService.processCommands(rover, commands);

		// Assert
		assertEquals(0, rover.getX());
		assertEquals(0, rover.getY());
		assertEquals('N', rover.getDirection());
		verify(roverRepository, times(4)).save(rover);
	}

	@Test
	void processCommands_shouldStopOnInvalidCommand() {
		// Arrange
		Rover rover = new Rover(0, 0, 'N');
		char[] commands = {'f', 'f', 'x', 'f', 'f'};

		// Act & Assert
		assertThrows(InvalidCommandException.class, () -> roverService.processCommands(rover, commands));
		assertEquals(0, rover.getX());
		assertEquals(2, rover.getY());
		assertEquals('N', rover.getDirection());
		verify(roverRepository, times(2)).save(rover);
	}
}
