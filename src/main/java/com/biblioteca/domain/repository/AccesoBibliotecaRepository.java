package com.biblioteca.domain.repository;

import com.biblioteca.domain.model.AccesoBiblioteca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para AccesoBiblioteca
 * Generado automáticamente desde diagrama UML
 */
@Repository
public interface AccesoBibliotecaRepository extends JpaRepository<AccesoBiblioteca, Long> {

    // Métodos de consulta básicos

    List<AccesoBiblioteca> findByOrderByCreatedAtDesc();

    List<AccesoBiblioteca> findTop10ByOrderByCreatedAtDesc();

    @Query("SELECT e FROM AccesoBiblioteca e ORDER BY e.createdAt DESC")
    List<AccesoBiblioteca> findAllOrderedByDate();

    // Buscar por ID con validación
    @Query("SELECT e FROM AccesoBiblioteca e WHERE e.id = :id")
    Optional<AccesoBiblioteca> findByIdSafe(@Param("id") Long id);


    // TODO: Agregar métodos de consulta específicos según necesidades del negocio
    // Ejemplo:
    // List<AccesoBiblioteca> findByNombreContaining(String nombre);
}