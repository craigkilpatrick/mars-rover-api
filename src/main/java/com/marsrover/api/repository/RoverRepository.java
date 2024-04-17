package com.marsrover.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marsrover.api.domain.Rover;

public interface RoverRepository extends JpaRepository<Rover, Long> {
  // Basic CRUD operations are automatically provided.
  // Custom methods can be defined here if specific queries are needed.
}
