package com.ut.tekir.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI tekirOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Tekir REST API")
                .version("3.0.0")
                .description("Tekir Ticari Otomasyon REST API")
                .contact(new Contact()
                    .name("Ozgur Yazilim A.S.")
                    .url("https://www.ozguryazilim.com.tr")))
            .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
            .components(new Components()
                .addSecuritySchemes("Bearer Authentication",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .bearerFormat("JWT")
                        .scheme("bearer")));
    }
}
