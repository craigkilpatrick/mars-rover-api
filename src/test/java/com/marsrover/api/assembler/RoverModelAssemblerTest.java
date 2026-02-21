package com.marsrover.api.assembler;

import com.marsrover.api.domain.Rover;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

class RoverModelAssemblerTest {

    private RoverModelAssembler assembler;

    @BeforeEach
    void setUp() {
        assembler = new RoverModelAssembler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void toModel_shouldContainRover() {
        Rover rover = spy(new Rover(0, 0, 'N'));
        doReturn(1L).when(rover).getId();

        EntityModel<Rover> model = assembler.toModel(rover);

        assertNotNull(model.getContent());
        assertEquals(rover, model.getContent());
    }

    @Test
    void toModel_shouldContainSelfAndRoversLinks() {
        Rover rover = spy(new Rover(0, 0, 'N'));
        doReturn(1L).when(rover).getId();

        EntityModel<Rover> model = assembler.toModel(rover);

        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("rovers"));
    }
}
