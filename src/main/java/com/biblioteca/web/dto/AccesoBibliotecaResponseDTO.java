package com.biblioteca.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * DTO de Response para AccesoBiblioteca
 * Generado automáticamente desde diagrama UML
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccesoBibliotecaResponseDTO {

    private Long id;


    
    private LocalDateTime fechaEntrada;

    
    private LocalDateTime fechaSalida;

    
    private String metodoAcceso;

    
    private String codigoQr;

    
    private Boolean estado;

    // Información del miembro para las respuestas
    private Long miembroId;
    private String miembroNombre;
    private String miembroApellido;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}