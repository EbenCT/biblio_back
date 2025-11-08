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
 * Entidad JPA para Miembro
 * Generada automáticamente desde diagrama UML
 
 
 */
@Entity
@Table(name = "miembro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Miembro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


        @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(name = "nombre", nullable = false)
    private String nombre;

        @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(name = "apellido", nullable = false)
    private String apellido;

        @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(name = "direccion", nullable = false)
    private String direccion;

        @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(name = "telefono", nullable = false)
    private String telefono;

        @NotNull
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

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