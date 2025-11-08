package com.biblioteca.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
/**
 * DTO de Request para Prestamo
 * Generado automáticamente desde diagrama UML
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoRequestDTO {


        @NotNull
    private LocalDateTime fechaPrestamo;

        @NotNull
    private LocalDateTime fechaDevolucionEsperada;

        @NotNull
    private LocalDateTime fechaDevolucionReal;

        @NotNull
    private Boolean estado;

        @NotNull
    private Double multa;

    // TODO: Agregar validaciones específicas según reglas de negocio
}