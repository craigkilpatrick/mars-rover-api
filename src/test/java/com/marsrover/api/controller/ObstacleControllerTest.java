package com.marsrover.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marsrover.api.assembler.ObstacleModelAssembler;
import com.marsrover.api.domain.Obstacle;
import com.marsrover.api.repository.ObstacleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ObstacleController.class)
class ObstacleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ObstacleRepository repository;

    @MockBean
    private ObstacleModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllObstaclesFound() throws Exception {
        Obstacle obstacle1 = spy(new Obstacle(5, 5));
        doReturn(1L).when(obstacle1).getId();
        Obstacle obstacle2 = spy(new Obstacle(10, 10));
        doReturn(2L).when(obstacle2).getId();

        Mockito.when(repository.findAll()).thenReturn(List.of(obstacle1, obstacle2));
        Mockito.when(assembler.toModel(Mockito.any(Obstacle.class)))
                .thenAnswer(invocation -> EntityModel.of(invocation.getArgument(0)));

        mockMvc.perform(get("/obstacles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.obstacleList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.obstacleList[0].x", is(5)))
                .andExpect(jsonPath("$._embedded.obstacleList[0].y", is(5)))
                .andExpect(jsonPath("$._embedded.obstacleList[1].x", is(10)))
                .andExpect(jsonPath("$._embedded.obstacleList[1].y", is(10)));
    }

    @Test
    void whenObstacleFoundById_thenReturnsObstacle() throws Exception {
        Obstacle obstacle = spy(new Obstacle(5, 5));
        doReturn(1L).when(obstacle).getId();

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(obstacle));
        Mockito.when(assembler.toModel(Mockito.any(Obstacle.class)))
                .thenReturn(EntityModel.of(obstacle));

        mockMvc.perform(get("/obstacles/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.x", is(5)))
                .andExpect(jsonPath("$.y", is(5)));
    }

    @Test
    void whenObstacleNotFoundById_thenThrowsException() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () ->
                mockMvc.perform(get("/obstacles/{id}", 99L)));
    }

    @Test
    void whenValidObstaclePosted_thenReturnsObstacleCreated() throws Exception {
        Obstacle newObstacle = spy(new Obstacle(5, 5));
        doReturn(1L).when(newObstacle).getId();
        EntityModel<Obstacle> obstacleModel = EntityModel.of(newObstacle);

        Mockito.when(repository.save(Mockito.any(Obstacle.class))).thenReturn(newObstacle);
        Mockito.when(assembler.toModel(Mockito.any(Obstacle.class))).thenReturn(obstacleModel);

        mockMvc.perform(post("/obstacles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newObstacle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.x", is(newObstacle.getX())))
                .andExpect(jsonPath("$.y", is(newObstacle.getY())));
    }

    @Test
    void whenObstacleDeleted_thenReturnsSuccessMessage() throws Exception {
        Obstacle obstacle = spy(new Obstacle(5, 5));
        doReturn(1L).when(obstacle).getId();

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(obstacle));
        Mockito.doNothing().when(repository).delete(obstacle);

        mockMvc.perform(delete("/obstacles/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Obstacle deleted successfully"));
    }

    @Test
    void whenObstacleToDeleteNotFound_thenThrowsException() {
        Mockito.when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () ->
                mockMvc.perform(delete("/obstacles/{id}", 99L)));
    }
}
