package com.marsrover.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

import com.marsrover.api.controller.RoverController;
import com.marsrover.api.domain.Rover;

@Component
public class RoverModelAssembler implements RepresentationModelAssembler<Rover, EntityModel<Rover>> {

    @Override
    @NonNull
    public EntityModel<Rover> toModel(@NonNull Rover rover) {
        return EntityModel.of(rover,
            linkTo(methodOn(RoverController.class).findRover(rover.getId())).withSelfRel(),
            linkTo(methodOn(RoverController.class).all()).withRel("rovers"));
    }
}
