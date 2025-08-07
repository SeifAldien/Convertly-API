package com.seif.convertly.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {
    @Bean
    public OpenAPI convertlyOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Convertly API")
                        .description("Unit Converter REST API")
                        .version("v1.0"));
    }
}
