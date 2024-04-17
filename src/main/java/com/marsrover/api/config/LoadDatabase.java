package com.marsrover.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.marsrover.api.domain.Rover;
import com.marsrover.api.repository.RoverRepository;

@Configuration
class LoadDatabase {
  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(RoverRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Rover(0, 0, 'N')));
      log.info("Preloading " + repository.save(new Rover(1, 2, 'N')));
      log.info("Preloading " + repository.save(new Rover(3, 3, 'W')));
      log.info("Preloading " + repository.save(new Rover(98, 98, 'E')));
    };
  }
}
