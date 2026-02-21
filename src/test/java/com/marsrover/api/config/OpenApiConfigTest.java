package com.marsrover.api.config;

import org.junit.jupiter.api.Test;
import org.springdoc.core.models.GroupedOpenApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenApiConfigTest {

    private final OpenApiConfig config = new OpenApiConfig();

    @Test
    void roversApi_shouldCreateGroupWithCorrectName() {
        GroupedOpenApi api = config.roversApi();

        assertNotNull(api);
        assertEquals("rovers", api.getGroup());
    }

    @Test
    void obstaclesApi_shouldCreateGroupWithCorrectName() {
        GroupedOpenApi api = config.obstaclesApi();

        assertNotNull(api);
        assertEquals("obstacles", api.getGroup());
    }
}
