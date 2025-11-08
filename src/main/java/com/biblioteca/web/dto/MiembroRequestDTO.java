package com.biblioteca.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
/**
 * DTO de Request para Miembro
 * Generado automáticamente desde diagrama UML
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiembroRequestDTO {


        @NotNull
    @NotBlank
    @Size(max = 255)
    private String nombre;

        @NotNull
    @NotBlank
    @Size(max = 255)
    private String apellido;

        @NotNull
    @NotBlank
    @Size(max = 255)
    private String direccion;

        @NotNull
    @NotBlank
    @Size(max = 255)
    private String telefono;

    // fechaRegistro es opcional - si no se proporciona, se asigna automáticamente
    private LocalDateTime fechaRegistro;

    // TODO: Agregar validaciones específicas según reglas de negocio
}