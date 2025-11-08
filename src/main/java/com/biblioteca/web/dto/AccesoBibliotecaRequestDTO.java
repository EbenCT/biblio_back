package com.biblioteca.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
/**
 * DTO de Request para AccesoBiblioteca
 * Generado automáticamente desde diagrama UML
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccesoBibliotecaRequestDTO {


        @NotNull
    private LocalDateTime fechaEntrada;

        @NotNull
    private LocalDateTime fechaSalida;

        @NotNull
    @NotBlank
    @Size(max = 255)
    private String metodoAcceso;

        @NotNull
    @NotBlank
    @Size(max = 255)
    private String codigoQr;

        @NotNull
    private Boolean estado;

    // TODO: Agregar validaciones específicas según reglas de negocio
}