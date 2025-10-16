package com.itic.userservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Gestion des utilisateurs")
public class UserController {
    public record HelloDto(String message) {}

    @GetMapping("/hello")
    @Operation(
            summary = "Get greeting from User Service",
            description = "Returns a simple greeting message from the User Service."
    )
    public HelloDto hello() {
        return new HelloDto("Hello from User Service!");
    }

}
