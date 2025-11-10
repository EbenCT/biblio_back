package com.biblioteca.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entidad JPA para Usuario
 * Generada automáticamente desde diagrama UML
 
 
 */
@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


        @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(name = "email", nullable = false)
    private String email;

        @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(name = "password", nullable = false)
    private String password;


    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
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