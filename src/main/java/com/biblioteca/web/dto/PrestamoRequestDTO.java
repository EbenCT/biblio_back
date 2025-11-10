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

    private LocalDateTime fechaDevolucionReal; // Opcional - solo cuando se devuelve

        @NotNull
    private Boolean estado;

    private Double multa; // Opcional - puede ser 0 o null inicialmente

        @NotNull
    private Long libroId;

        @NotNull
    private Long miembroId;

    // Constructor para crear préstamos sin IDs (para actualizaciones que mantienen las relaciones)
    public PrestamoRequestDTO(LocalDateTime fechaPrestamo, LocalDateTime fechaDevolucionEsperada, 
                             LocalDateTime fechaDevolucionReal, Boolean estado, Double multa) {
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.estado = estado;
        this.multa = multa;
    }

    // TODO: Agregar validaciones específicas según reglas de negocio
}