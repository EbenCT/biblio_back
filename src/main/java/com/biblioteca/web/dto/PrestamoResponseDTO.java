package com.biblioteca.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * DTO de Response para Prestamo
 * Generado automáticamente desde diagrama UML
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoResponseDTO {

    private Long id;


    
    private LocalDateTime fechaPrestamo;

    
    private LocalDateTime fechaDevolucionEsperada;

    
    private LocalDateTime fechaDevolucionReal;

    
    private Boolean estado;

    
    private Double multa;

    private Long libroId;

    private Long miembroId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}