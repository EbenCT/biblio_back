package com.biblioteca.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
/**
 * DTO de Request para Libro
 * Generado automáticamente desde diagrama UML
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroRequestDTO {


        @NotNull
    @NotBlank
    @Size(max = 255)
    private String titulo;

        @NotNull
    @NotBlank
    @Size(max = 255)
    private String autor;

        @NotNull
    @NotBlank
    @Size(max = 255)
    private String categoria;

        @NotNull
    private Boolean disponible;

    // TODO: Agregar validaciones específicas según reglas de negocio
}