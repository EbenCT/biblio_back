package com.biblioteca.domain.repository;

import com.biblioteca.domain.model.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para Miembro
 * Generado automáticamente desde diagrama UML
 */
@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Long> {

    // Métodos de consulta básicos

    List<Miembro> findByOrderByCreatedAtDesc();

    List<Miembro> findTop10ByOrderByCreatedAtDesc();

    @Query("SELECT e FROM Miembro e ORDER BY e.createdAt DESC")
    List<Miembro> findAllOrderedByDate();

    // Buscar por ID con validación
    @Query("SELECT e FROM Miembro e WHERE e.id = :id")
    Optional<Miembro> findByIdSafe(@Param("id") Long id);


    // TODO: Agregar métodos de consulta específicos según necesidades del negocio
    // Ejemplo:
    // List<Miembro> findByNombreContaining(String nombre);
}