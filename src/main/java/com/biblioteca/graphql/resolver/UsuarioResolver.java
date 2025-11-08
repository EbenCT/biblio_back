package com.biblioteca.graphql.resolver;

import com.biblioteca.domain.service.UsuarioService;
import com.biblioteca.web.dto.UsuarioRequestDTO;
import com.biblioteca.web.dto.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GraphQL Resolver para Usuario
 * Maneja todas las operaciones CRUD de usuarios via GraphQL
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class UsuarioResolver {

    private final UsuarioService usuarioService;

    // ==================== QUERIES ====================

    /**
     * Obtener todos los usuarios
     */
    @QueryMapping
    public List<UsuarioResponseDTO> usuarios() {
        log.debug("GraphQL: Obteniendo todos los usuarios");
        return usuarioService.findAll();
    }

    /**
     * Obtener usuario por ID
     */
    @QueryMapping
    public UsuarioResponseDTO usuario(@Argument String id) {
        log.debug("GraphQL: Obteniendo usuario con ID: {}", id);
        Long idLong = Long.parseLong(id);
        return usuarioService.findById(idLong);
    }

    /**
     * Buscar usuarios por email (búsqueda parcial)
     */
    @QueryMapping
    public List<UsuarioResponseDTO> usuariosPorEmail(@Argument String emailParcial) {
        log.debug("GraphQL: Buscando usuarios con email que contenga: {}", emailParcial);
        return usuarioService.findAll().stream()
                .filter(usuario -> usuario.getEmail() != null && 
                       usuario.getEmail().toLowerCase().contains(emailParcial.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Obtener usuarios ordenados por fecha de creación
     */
    @QueryMapping
    public List<UsuarioResponseDTO> usuariosRecientes() {
        log.debug("GraphQL: Obteniendo usuarios ordenados por fecha de creación");
        return usuarioService.findAll().stream()
                .sorted((u1, u2) -> {
                    if (u1.getCreatedAt() == null && u2.getCreatedAt() == null) return 0;
                    if (u1.getCreatedAt() == null) return 1;
                    if (u2.getCreatedAt() == null) return -1;
                    return u2.getCreatedAt().compareTo(u1.getCreatedAt()); // Más recientes primero
                })
                .collect(Collectors.toList());
    }

    /**
     * Contar total de usuarios
     */
    @QueryMapping
    public Integer totalUsuarios() {
        log.debug("GraphQL: Contando total de usuarios");
        return usuarioService.findAll().size();
    }

    // ==================== MUTATIONS ====================

    /**
     * Crear nuevo usuario (CRUD básico - diferente del registro con AuthResolver)
     */
    @MutationMapping
    public UsuarioResponseDTO crearUsuario(@Argument UsuarioInput input) {
        log.info("GraphQL: Creando nuevo usuario: {}", input);
        
        // Convertimos el input GraphQL al DTO REST existente
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO(
                input.getEmail(),
                input.getPassword()
        );
        
        return usuarioService.create(requestDTO);
    }

    /**
     * Actualizar usuario existente
     */
    @MutationMapping
    public UsuarioResponseDTO actualizarUsuario(@Argument String id, @Argument UsuarioInput input) {
        log.info("GraphQL: Actualizando usuario con ID: {}", id);
        
        Long idLong = Long.parseLong(id);
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO(
                input.getEmail(),
                input.getPassword()
        );
        
        return usuarioService.update(idLong, requestDTO);
    }

    /**
     * Eliminar usuario
     */
    @MutationMapping
    public Boolean eliminarUsuario(@Argument String id) {
        log.info("GraphQL: Eliminando usuario con ID: {}", id);
        try {
            Long idLong = Long.parseLong(id);
            usuarioService.delete(idLong);
            return true;
        } catch (Exception e) {
            log.error("Error eliminando usuario: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Actualizar solo email de usuario
     */
    @MutationMapping
    public UsuarioResponseDTO actualizarEmailUsuario(@Argument String id, @Argument String nuevoEmail) {
        log.info("GraphQL: Actualizando email del usuario ID: {} a {}", id, nuevoEmail);
        
        try {
            Long idLong = Long.parseLong(id);
            UsuarioResponseDTO usuario = usuarioService.findById(idLong);
            
            // Crear DTO con email actualizado y password existente
            UsuarioRequestDTO updateDTO = new UsuarioRequestDTO(
                    nuevoEmail,
                    usuario.getPassword() // Mantener password existente
            );
            
            return usuarioService.update(idLong, updateDTO);
        } catch (Exception e) {
            log.error("Error actualizando email: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar email: " + e.getMessage());
        }
    }

    /**
     * Actualizar solo password de usuario
     */
    @MutationMapping
    public UsuarioResponseDTO actualizarPasswordUsuario(@Argument String id, @Argument String nuevoPassword) {
        log.info("GraphQL: Actualizando password del usuario ID: {}", id);
        
        try {
            Long idLong = Long.parseLong(id);
            UsuarioResponseDTO usuario = usuarioService.findById(idLong);
            
            // Crear DTO con password actualizado y email existente
            UsuarioRequestDTO updateDTO = new UsuarioRequestDTO(
                    usuario.getEmail(), // Mantener email existente
                    nuevoPassword
            );
            
            return usuarioService.update(idLong, updateDTO);
        } catch (Exception e) {
            log.error("Error actualizando password: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar password: " + e.getMessage());
        }
    }

    // Clase interna para el input GraphQL
    public static class UsuarioInput {
        private String email;
        private String password;

        // Getters y setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        @Override
        public String toString() {
            return String.format("UsuarioInput{email='%s', password='[PROTECTED]'}", email);
        }
    }
}