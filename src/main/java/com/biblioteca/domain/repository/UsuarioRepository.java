package com.biblioteca.domain.repository;

import com.biblioteca.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para Usuario
 * Generado automáticamente desde diagrama UML
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Métodos de consulta básicos

    List<Usuario> findByOrderByCreatedAtDesc();

    List<Usuario> findTop10ByOrderByCreatedAtDesc();

    @Query("SELECT e FROM Usuario e ORDER BY e.createdAt DESC")
    List<Usuario> findAllOrderedByDate();

    // Buscar por ID con validación
    @Query("SELECT e FROM Usuario e WHERE e.id = :id")
    Optional<Usuario> findByIdSafe(@Param("id") Long id);

    // Métodos específicos para entidad de usuario
    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmailIgnoreCase(@Param("email") String email);

    boolean existsByEmail(String email);

    // TODO: Agregar métodos de consulta específicos según necesidades del negocio
    // Ejemplo:
    // List<Usuario> findByNombreContaining(String nombre);
}