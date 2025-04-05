package com.marsrover.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.marsrover.api.domain.Rover;
import com.marsrover.api.domain.Obstacle;
import com.marsrover.api.repository.RoverRepository;
import com.marsrover.api.repository.ObstacleRepository;

@Configuration
class LoadDatabase {
  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(RoverRepository roverRepository, ObstacleRepository obstacleRepository) {

    return args -> {
      // Preload some rovers
      log.info("Preloading " + roverRepository.save(new Rover(0, 0, 'N')));
      log.info("Preloading " + roverRepository.save(new Rover(1, 2, 'N')));
      log.info("Preloading " + roverRepository.save(new Rover(3, 3, 'W')));
      log.info("Preloading " + roverRepository.save(new Rover(98, 98, 'E')));

      // Preload some obstacles
      log.info("Preloading " + obstacleRepository.save(new Obstacle(5, 5)));
      log.info("Preloading " + obstacleRepository.save(new Obstacle(10, 10)));
      log.info("Preloading " + obstacleRepository.save(new Obstacle(15, 15)));
      log.info("Preloading " + obstacleRepository.save(new Obstacle(20, 20)));
      log.info("Preloading " + obstacleRepository.save(new Obstacle(25, 25)));
    };
  }
}
