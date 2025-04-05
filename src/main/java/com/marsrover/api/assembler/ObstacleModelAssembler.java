package com.marsrover.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

import com.marsrover.api.controller.ObstacleController;
import com.marsrover.api.domain.Obstacle;

@Component
public class ObstacleModelAssembler implements RepresentationModelAssembler<Obstacle, EntityModel<Obstacle>> {

    @Override
    @NonNull
    public EntityModel<Obstacle> toModel(@NonNull Obstacle obstacle) {
        return EntityModel.of(obstacle,
            linkTo(methodOn(ObstacleController.class).findObstacle(obstacle.getId())).withSelfRel(),
            linkTo(methodOn(ObstacleController.class).all()).withRel("obstacles"));
    }
}
