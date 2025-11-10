package com.biblioteca.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuesta de autenticación
 * Generado automáticamente desde diagrama UML
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private String token;
    private String email;
    private String message;
    @Builder.Default
    private String type = "Bearer";
    
    // Campo para GraphQL - usuario completo
    private UsuarioDTO user;
}