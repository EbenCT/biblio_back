package com.biblioteca.auth.controller;

import com.biblioteca.auth.dto.AuthResponseDTO;
import com.biblioteca.auth.dto.LoginRequestDTO;
import com.biblioteca.auth.dto.RegisterRequestDTO;
import com.biblioteca.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * Controlador de autenticación
 * Generado automáticamente desde diagrama UML
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        log.info("REST: Login attempt for {}", request.getEmail());
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        log.info("REST: Register attempt for {}", request.getEmail());
        AuthResponseDTO response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken() {
        return ResponseEntity.ok("Token válido");
    }
}