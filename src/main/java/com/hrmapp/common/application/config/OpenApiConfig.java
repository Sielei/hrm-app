package com.hrmapp.common.application.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {
    @Bean
    OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("HRM application API")
                        .description("")
                        .contact(new Contact().email("hsielei@gmail.com"))
                        .version("1.0.0")
                        .license(new License().name("MIT")));
    }
}
