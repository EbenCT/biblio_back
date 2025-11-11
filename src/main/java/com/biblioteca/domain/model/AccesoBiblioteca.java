package com.biblioteca.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Entidad JPA para AccesoBiblioteca
 * Generada automáticamente desde diagrama UML
 
 
 */
@Entity
@Table(name = "acceso_biblioteca")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccesoBiblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


        @NotNull
    @Column(name = "fecha_entrada", nullable = false)
    private LocalDateTime fechaEntrada;

    @Column(name = "fecha_salida", nullable = true)
    private LocalDateTime fechaSalida;

        @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(name = "metodo_acceso", nullable = false)
    private String metodoAcceso;

        @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(name = "codigo_qr", nullable = false)
    private String codigoQr;

        @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "miembro_id")
    private Miembro miembro;

    // Campos de auditoría
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}