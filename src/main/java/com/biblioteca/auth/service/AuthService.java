package com.biblioteca.auth.service;

import com.biblioteca.auth.dto.AuthResponseDTO;
import com.biblioteca.auth.dto.LoginRequestDTO;
import com.biblioteca.auth.dto.RegisterRequestDTO;
import com.biblioteca.auth.dto.UsuarioDTO;
import com.biblioteca.config.JwtUtil;
import com.biblioteca.domain.model.Usuario;
import com.biblioteca.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de autenticación
 * Generado automáticamente desde diagrama UML
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO login(LoginRequestDTO request) {
        log.info("Intento de login para: {}", request.getEmail());

        // Autenticar usuario
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Cargar detalles del usuario
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        // Generar token
        String token = jwtUtil.generateToken(userDetails);

        // Obtener información del usuario
        Usuario user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        log.info("Login exitoso para: {}", request.getEmail());

        // Crear UsuarioDTO para GraphQL
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
            .id(user.getId().toString())
            .email(user.getEmail())
            .build();

        return AuthResponseDTO.builder()
            .token(token)
            .email(user.getEmail())
            .message("Login exitoso")
            .user(usuarioDTO)
            .build();
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        log.info("Registro de nuevo usuario: {}", request.getEmail());

        // Verificar si el usuario ya existe
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear nuevo usuario
        Usuario user = new Usuario();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Mapear automáticamente otros campos de la entidad


        Usuario savedUser = userRepository.save(user);

        // Generar token
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        log.info("Usuario registrado exitosamente: {}", request.getEmail());

        // Crear UsuarioDTO para GraphQL
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
            .id(savedUser.getId().toString())
            .email(savedUser.getEmail())
            .build();

        return AuthResponseDTO.builder()
            .token(token)
            .email(savedUser.getEmail())
            .message("Usuario registrado exitosamente")
            .user(usuarioDTO)
            .build();
    }
}