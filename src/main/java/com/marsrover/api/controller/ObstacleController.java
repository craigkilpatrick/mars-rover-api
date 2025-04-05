package com.marsrover.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marsrover.api.domain.Obstacle;
import com.marsrover.api.repository.ObstacleRepository;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Obstacle Controller", description = "API endpoints for managing obstacles")
public class ObstacleController {

    private final ObstacleRepository repository;

    public ObstacleController(ObstacleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/obstacles")
    @Operation(summary = "Get all obstacles", description = "Retrieve a list of all obstacles")
    @ApiResponse(responseCode = "200", description = "Obstacles found", content = @Content(mediaType = "application/json"))
    public ResponseEntity<List<Obstacle>> all() {
        List<Obstacle> obstacles = repository.findAll();
        return ResponseEntity.ok(obstacles);
    }

    @PostMapping("/obstacles")
    @Operation(summary = "Create a new obstacle", description = "Create a new obstacle with the provided details")
    @ApiResponse(responseCode = "200", description = "Obstacle created successfully", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Invalid obstacle data provided", content = @Content(mediaType = "application/json"))
    public ResponseEntity<Obstacle> newObstacle(@RequestBody @Valid Obstacle newObstacle) {
        Obstacle savedObstacle = repository.save(newObstacle);
        return ResponseEntity.ok(savedObstacle);
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
            .orElse(ResponseEntity.notFound().build());
    }
}
