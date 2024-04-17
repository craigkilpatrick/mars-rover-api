package com.marsrover.api.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
      .group("rovers")
      .pathsToMatch("/rovers/**")
      .addOpenApiCustomizer(openApi -> openApi.getInfo().setVersion("1.0.0"))
      .build();
  }
}
