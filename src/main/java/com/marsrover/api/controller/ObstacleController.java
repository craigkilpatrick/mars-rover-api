package com.marsrover.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marsrover.api.domain.Obstacle;
import com.marsrover.api.repository.ObstacleRepository;
import com.marsrover.api.exception.ObstacleNotFoundException;
import com.marsrover.api.assembler.ObstacleModelAssembler;

import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Obstacle Controller", description = "API endpoints for managing obstacles")
public class ObstacleController {

    private final ObstacleRepository repository;
    private final ObstacleModelAssembler assembler;

    public ObstacleController(ObstacleRepository repository, ObstacleModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/obstacles")
    @Operation(summary = "Get all obstacles", description = "Retrieve a list of all obstacles")
    @ApiResponse(responseCode = "200", description = "Obstacles found", content = @Content(mediaType = "application/json"))
    public ResponseEntity<CollectionModel<EntityModel<Obstacle>>> all() {
        List<EntityModel<Obstacle>> obstacles = repository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return ResponseEntity.ok(
            CollectionModel.of(obstacles, linkTo(methodOn(ObstacleController.class).all()).withSelfRel())
        );
    }

    @PostMapping("/obstacles")
    @Operation(summary = "Create a new obstacle", description = "Create a new obstacle with the provided details")
    @ApiResponse(responseCode = "200", description = "Obstacle created successfully", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Invalid obstacle data provided", content = @Content(mediaType = "application/json"))
    public ResponseEntity<EntityModel<Obstacle>> newObstacle(@RequestBody @Valid Obstacle newObstacle) {
        Obstacle savedObstacle = repository.save(newObstacle);
        return ResponseEntity.ok(assembler.toModel(savedObstacle));
    }

    @GetMapping("/obstacles/{id}")
    @Operation(summary = "Get an obstacle by ID", description = "Retrieve an obstacle by its ID")
    @ApiResponse(responseCode = "200", description = "Obstacle found", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "Obstacle not found", content = @Content(mediaType = "application/json"))
    public ResponseEntity<EntityModel<Obstacle>> findObstacle(@PathVariable Long id) {
        Obstacle obstacle = repository.findById(id)
            .orElseThrow(() -> new ObstacleNotFoundException("Obstacle not found with id: " + id));

        return ResponseEntity.ok(assembler.toModel(obstacle));
    }

    @DeleteMapping("/obstacles/{id}")
    @Operation(summary = "Delete an obstacle", description = "Delete an obstacle by its ID")
    @ApiResponse(responseCode = "200", description = "Obstacle deleted successfully")
    @ApiResponse(responseCode = "404", description = "Obstacle not found", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> deleteObstacle(@PathVariable Long id) {
        return repository.findById(id)
            .map(obstacle -> {
                repository.delete(obstacle);
                return ResponseEntity.ok().body("Obstacle deleted successfully");
            })
            .orElseThrow(() -> new ObstacleNotFoundException("Obstacle not found with id: " + id));
    }
}
