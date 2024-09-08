package com.sbaldass.sneakersstore.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sneakers Store API")
                        .description("API for managing sneaker orders and details")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("samuelbaldasso93@gmail.com")));
    }
}
