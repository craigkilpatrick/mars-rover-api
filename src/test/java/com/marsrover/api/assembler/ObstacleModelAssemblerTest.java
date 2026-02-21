package com.marsrover.api.assembler;

import com.marsrover.api.domain.Obstacle;
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

class ObstacleModelAssemblerTest {

    private ObstacleModelAssembler assembler;

    @BeforeEach
    void setUp() {
        assembler = new ObstacleModelAssembler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void toModel_shouldContainObstacle() {
        Obstacle obstacle = spy(new Obstacle(5, 5));
        doReturn(1L).when(obstacle).getId();

        EntityModel<Obstacle> model = assembler.toModel(obstacle);

        assertNotNull(model.getContent());
        assertEquals(obstacle, model.getContent());
    }

    @Test
    void toModel_shouldContainSelfAndObstaclesLinks() {
        Obstacle obstacle = spy(new Obstacle(5, 5));
        doReturn(1L).when(obstacle).getId();

        EntityModel<Obstacle> model = assembler.toModel(obstacle);

        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("obstacles"));
    }
}
