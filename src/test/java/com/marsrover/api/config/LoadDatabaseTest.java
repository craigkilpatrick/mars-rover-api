package com.marsrover.api.config;

import com.marsrover.api.repository.ObstacleRepository;
import com.marsrover.api.repository.RoverRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LoadDatabaseTest {

    @Autowired
    private RoverRepository roverRepository;

    @Autowired
    private ObstacleRepository obstacleRepository;

    @Test
    void shouldPreloadFourRovers() {
        assertEquals(4, roverRepository.count());
    }

    @Test
    void shouldPreloadFiveObstacles() {
        assertEquals(5, obstacleRepository.count());
    }

    @Test
    void shouldPreloadRoverAtOrigin() {
        assertTrue(roverRepository.findAll().stream()
                .anyMatch(r -> r.getX() == 0 && r.getY() == 0 && r.getDirection() == 'N'));
    }

    @Test
    void shouldPreloadObstacleAtFiveFive() {
        assertTrue(obstacleRepository.existsByXAndY(5, 5));
    }
}
