package com.biblioteca.domain.repository;

import com.biblioteca.domain.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para Libro
 * Generado automáticamente desde diagrama UML
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Métodos de consulta básicos

    List<Libro> findByOrderByCreatedAtDesc();

    List<Libro> findTop10ByOrderByCreatedAtDesc();

    @Query("SELECT e FROM Libro e ORDER BY e.createdAt DESC")
    List<Libro> findAllOrderedByDate();

    // Buscar por ID con validación
    @Query("SELECT e FROM Libro e WHERE e.id = :id")
    Optional<Libro> findByIdSafe(@Param("id") Long id);


    // TODO: Agregar métodos de consulta específicos según necesidades del negocio
    // Ejemplo:
    // List<Libro> findByNombreContaining(String nombre);
}