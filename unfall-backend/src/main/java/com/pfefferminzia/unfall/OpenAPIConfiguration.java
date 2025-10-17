package com.pfefferminzia.unfall;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
    title = "Unfall Versicherungs API",
    version = "1.0.0",
    description = "API für Vertragsmanagement und Tarifberechnung"
))
public class OpenAPIConfiguration {

    @Bean
    public GroupedOpenApi contractApi() {
        return GroupedOpenApi.builder()
            .group("contracts")
            .pathsToMatch("/api/contracts/**")
            .build();
    }
}
