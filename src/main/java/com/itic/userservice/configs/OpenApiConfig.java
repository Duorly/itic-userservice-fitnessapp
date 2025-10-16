package com.itic.userservice.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI userServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service API (Fitness App)")
                        .description("""
                            API de gestion des utilisateurs, profils, statistiques et badges.
                        """)
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                        .contact(new Contact().name("GROUP 1 M2 - Itic Paris").email("support@example.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8081/api").description("Local"),
                        new Server().url("https://api.example.com/user-service").description("Prod")
                ));
    }

    // Groupe Users
    @Bean
    public GroupedOpenApi usersApi() {
        return GroupedOpenApi.builder()
                .group("Users")
                .pathsToMatch("/users/**")
                .build();
    }

    // Groupe Profiles
    @Bean
    public GroupedOpenApi profilesApi() {
        return GroupedOpenApi.builder()
                .group("Profiles")
                .pathsToMatch("/users/*/profile")
                .build();
    }

    // Groupe Stats
    @Bean
    public GroupedOpenApi statsApi() {
        return GroupedOpenApi.builder()
                .group("Stats")
                .pathsToMatch("/users/*/stats/**")
                .build();
    }

    // Groupe Badges
    @Bean
    public GroupedOpenApi badgesApi() {
        return GroupedOpenApi.builder()
                .group("Badges")
                .pathsToMatch("/badges/**", "/users/*/badges/**")
                .build();
    }
}