package com.marsrover.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marsrover.api.domain.Obstacle;

/**
 * Repository for managing Obstacle entities.
 */
@Repository
public interface ObstacleRepository extends JpaRepository<Obstacle, Long> {

    /**
     * Checks if an obstacle exists at the specified coordinates.
     *
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return true if an obstacle exists at the specified coordinates, false otherwise.
     */
    boolean existsByXAndY(int x, int y);
}
