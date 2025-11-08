package com.biblioteca.graphql.resolver;

import com.biblioteca.auth.dto.AuthResponseDTO;
import com.biblioteca.auth.dto.LoginRequestDTO;
import com.biblioteca.auth.dto.RegisterRequestDTO;
import com.biblioteca.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

/**
 * GraphQL Resolver para autenticación
 * Maneja login y registro via GraphQL
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthResolver {

    private final AuthService authService;

    /**
     * Registrar nuevo usuario via GraphQL
     */
    @MutationMapping
    public AuthResponseDTO registrar(@Argument RegisterRequestDTO registerRequest) {
        log.info("GraphQL: Register attempt for {}", registerRequest.getEmail());
        return authService.register(registerRequest);
    }

    /**
     * Login de usuario via GraphQL
     */
    @MutationMapping
    public AuthResponseDTO login(@Argument LoginRequestDTO loginRequest) {
        log.info("GraphQL: Login attempt for {}", loginRequest.getEmail());
        return authService.login(loginRequest);
    }
}