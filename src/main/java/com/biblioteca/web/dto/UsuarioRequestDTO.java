package com.biblioteca.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
/**
 * DTO de Request para Usuario
 * Generado automáticamente desde diagrama UML
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {


        @NotNull
    @NotBlank
    @Size(max = 255)
    private String email;

        @NotNull
    @NotBlank
    @Size(max = 255)
    private String password;

    // TODO: Agregar validaciones específicas según reglas de negocio
}