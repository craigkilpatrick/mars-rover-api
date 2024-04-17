package com.marsrover.api.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.marsrover.api.assembler.RoverModelAssembler;
import com.marsrover.api.domain.Rover;
import com.marsrover.api.exception.RoverNotFoundException;
import com.marsrover.api.repository.RoverRepository;
import com.marsrover.api.service.RoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;

@RestController
@Tag(name = "Rover Controller", description = "API endpoints for managing rovers")
public
class RoverController {

  private final RoverRepository repository;

  private final RoverModelAssembler assembler;

  @Autowired
  private RoverService roverService;

  public RoverController(RoverRepository repository, RoverModelAssembler assembler, RoverService roverService) {
    this.repository = repository;
    this.assembler = assembler;
    this.roverService = roverService;
  }

  @GetMapping("/rovers")
  @Operation(summary = "Get all rovers", description = "Retrieve a list of all rovers")
  @ApiResponse(responseCode = "200", description = "Rovers found", content = @Content(mediaType = "application/json"))
  public ResponseEntity<CollectionModel<EntityModel<Rover>>> all() {
    List<EntityModel<Rover>> rovers = repository.findAll().stream()
      .map(assembler::toModel)
      .collect(Collectors.toList());

    return ResponseEntity.ok(CollectionModel.of(rovers, linkTo(methodOn(RoverController.class).all()).withSelfRel()));
  }

  @PostMapping("/rovers")
  @Operation(summary = "Create a new rover", description = "Create a new rover with the provided details")
  @ApiResponse(responseCode = "200", description = "Rover created successfully", content = @Content(mediaType = "application/json"))
  @ApiResponse(responseCode = "400", description = "Invalid rover data provided", content = @Content(mediaType = "application/json"))
  public ResponseEntity<EntityModel<Rover>> newRover(@RequestBody @Valid Rover newRover) {
    Rover savedRover = repository.save(newRover);
    EntityModel<Rover> model = assembler.toModel(savedRover);
    return ResponseEntity.ok(model);
  }

  @GetMapping("/rovers/{id}")
  @Operation(summary = "Get a rover by ID", description = "Retrieve a rover by its ID")
  @ApiResponse(responseCode = "200", description = "Rover found", content = @Content(mediaType = "application/json"))
  @ApiResponse(responseCode = "404", description = "Rover not found", content = @Content(mediaType = "application/json"))
  public ResponseEntity<EntityModel<Rover>> findRover(@PathVariable Long id) {
    Rover rover = repository.findById(id)
        .orElseThrow(() -> new RoverNotFoundException(id));

    EntityModel<Rover> model = assembler.toModel(rover);
    return ResponseEntity.ok(model);
  }

  @PostMapping("/rovers/{id}/commands")
  @Operation(summary = "Process commands for a rover", description = "Process a series of commands for a rover")
  @ApiResponse(responseCode = "200", description = "Commands processed successfully", content = @Content(mediaType = "application/json"))
  @ApiResponse(responseCode = "400", description = "Invalid command provided", content = @Content(mediaType = "application/json"))
  @ApiResponse(responseCode = "404", description = "Rover not found", content = @Content(mediaType = "application/json"))
  public ResponseEntity<EntityModel<Rover>> sendCommands(@PathVariable Long id, @RequestBody char[] commands) {
    Rover rover = repository.findById(id)
      .orElseThrow(() -> new RoverNotFoundException(id));

    roverService.processCommands(rover, commands);

    EntityModel<Rover> model = assembler.toModel(rover);
    return ResponseEntity.ok(model);
  }

  @DeleteMapping("/rovers/{id}")
  @Operation(summary = "Delete a rover", description = "Delete a rover by its ID")
  @ApiResponse(responseCode = "200", description = "Rover deleted successfully")
  @ApiResponse(responseCode = "404", description = "Rover not found", content = @Content(mediaType = "application/json"))
  public ResponseEntity<?> deleteRover(@PathVariable Long id) {
    return repository.findById(id)
      .map(rover -> {
        repository.delete(rover);
        return ResponseEntity.ok().body("Rover deleted successfully");
      })
      .orElseThrow(() -> new RoverNotFoundException(id));
  }
}
