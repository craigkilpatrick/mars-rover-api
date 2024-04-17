package com.marsrover.api.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;

import com.marsrover.api.domain.Rover;
import com.marsrover.api.repository.RoverRepository;
import com.marsrover.api.assembler.RoverModelAssembler;
import com.marsrover.api.service.RoverService;

import java.util.List;
import java.util.Optional;

@WebMvcTest(RoverController.class)
class RoverControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RoverRepository repository;

	@MockBean
	private RoverModelAssembler assembler;

	@MockBean
	private RoverService roverService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testGetAllRoversFound() throws Exception {
		// Arrange
		Rover rover1 = spy(new Rover(0, 0, 'N'));
		doReturn(1L).when(rover1).getId();
		Rover rover2 = spy(new Rover(1, 2, 'N'));
		doReturn(2L).when(rover2).getId();

		List<Rover> allRovers = List.of(rover1, rover2);

		Mockito.when(repository.findAll()).thenReturn(allRovers);
		Mockito.when(assembler.toModel(Mockito.any(Rover.class)))
						.thenAnswer(invocation -> EntityModel.of(invocation.getArgument(0)));

		// Act & Assert
		mockMvc.perform(get("/rovers"))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$._embedded.roverList", hasSize(2)))
						.andExpect(jsonPath("$._embedded.roverList[0].id", is(1)))
						.andExpect(jsonPath("$._embedded.roverList[0].x", is(0)))
						.andExpect(jsonPath("$._embedded.roverList[0].y", is(0)))
						.andExpect(jsonPath("$._embedded.roverList[0].direction", is("N")))
						.andExpect(jsonPath("$._embedded.roverList[1].id", is(2)))
						.andExpect(jsonPath("$._embedded.roverList[1].x", is(1)))
						.andExpect(jsonPath("$._embedded.roverList[1].y", is(2)))
						.andExpect(jsonPath("$._embedded.roverList[1].direction", is("N")));
	}

	@Test
	void whenRoverFoundById_thenReturnsRover() throws Exception {
		// Arrange
		Rover rover = spy(new Rover(0, 0, 'N'));
		doReturn(1L).when(rover).getId();

		Mockito.when(repository.findById(1L)).thenReturn(Optional.of(rover));
		Mockito.when(assembler.toModel(Mockito.any(Rover.class)))
				.thenReturn(EntityModel.of(rover));

		// Act & Assert
		mockMvc.perform(get("/rovers/{id}", 1L))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.id", is(1)))
						.andExpect(jsonPath("$.x", is(0)))
						.andExpect(jsonPath("$.y", is(0)))
						.andExpect(jsonPath("$.direction", is("N")));
	}

	@Test
	void whenRoverNotFoundById_thenReturnsNotFound() throws Exception {
		// Arrange
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		// Act & Assert
		mockMvc.perform(get("/rovers/{id}", 99L))
					.andExpect(status().isNotFound());
	}

	@Test
	void whenRoverDeleted_thenReturnsSuccessMessage() throws Exception {
		// Arrange
		Rover rover = spy(new Rover(0, 0, 'N'));
		doReturn(1L).when(rover).getId();

		Mockito.when(repository.findById(1L)).thenReturn(Optional.of(rover));
		Mockito.doNothing().when(repository).delete(rover);

		// Act & Assert
		mockMvc.perform(delete("/rovers/{id}", 1L))
						.andExpect(status().isOk())
						.andExpect(content().string("Rover deleted successfully"));
	}

	@Test
	void whenRoverNotFound_thenReturnsNotFound() throws Exception {
		// Arrange
		Mockito.when(repository.findById(99L)).thenReturn(Optional.empty());

		// Act & Assert
		mockMvc.perform(delete("/rovers/{id}", 99L))
					.andExpect(status().isNotFound());
	}

	@Test
	void whenValidRoverPosted_thenReturnsRoverCreated() throws Exception {
		// Arrange
		Rover newRover = spy(new Rover(0, 0, 'N'));
		doReturn(1L).when(newRover).getId();
		EntityModel<Rover> roverModel = EntityModel.of(newRover);

		Mockito.when(repository.save(Mockito.any(Rover.class))).thenReturn(newRover);
		Mockito.when(assembler.toModel(Mockito.any(Rover.class))).thenReturn(roverModel);

		// Act & Assert
		mockMvc.perform(post("/rovers")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newRover)))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.x", is(newRover.getX())))
						.andExpect(jsonPath("$.y", is(newRover.getY())))
						.andExpect(jsonPath("$.direction", is(String.valueOf(newRover.getDirection()))));
	}

	@Test
	void whenInvalidRoverPosted_thenReturnsBadRequest() throws Exception {
		// Arrange
		Rover invalidRover = new Rover(-1, -1, 'Z');

		// Act & Assert
		mockMvc.perform(post("/rovers")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(invalidRover)))
						.andExpect(status().isBadRequest());
	}

	@Test
	void whenCommandsSentToRover_thenProcessedSuccessfully() throws Exception {
		// Arrange
		Rover rover = spy(new Rover(0, 0, 'N'));
		doReturn(1L).when(rover).getId();

		EntityModel<Rover> roverModel = EntityModel.of(rover);
		char[] commands = {'L', 'R', 'M'};

		Mockito.when(repository.findById(1L)).thenReturn(Optional.of(rover));
		Mockito.when(assembler.toModel(Mockito.any(Rover.class))).thenReturn(roverModel);

		// Act & Assert
		mockMvc.perform(post("/rovers/{id}/commands", 1L)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(commands)))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.id", is(1)))
						.andExpect(jsonPath("$.x", is(rover.getX())))
						.andExpect(jsonPath("$.y", is(rover.getY())))
						.andExpect(jsonPath("$.direction", is(String.valueOf(rover.getDirection()))));
	}

	@Test
	void whenCommandsSentToNonExistentRover_thenNotFound() throws Exception {
		char[] commands = {'L', 'R', 'M'};

		Mockito.when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

		mockMvc.perform(post("/rovers/{id}/commands", 99L)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(commands)))
						.andExpect(status().isNotFound());
	}
}
