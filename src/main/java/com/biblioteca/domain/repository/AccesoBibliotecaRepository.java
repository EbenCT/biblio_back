package com.biblioteca.domain.repository;

import com.biblioteca.domain.model.AccesoBiblioteca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
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


    // ==================== MÉTODOS ESPECÍFICOS PARA INGRESO/EGRESO ====================

    /**
     * Buscar acceso activo (sin fecha de salida) de un miembro específico
     */
    @Query("SELECT a FROM AccesoBiblioteca a WHERE a.miembro.id = :miembroId AND a.estado = true AND a.fechaSalida IS NULL")
    Optional<AccesoBiblioteca> findActivoByMiembro(@Param("miembroId") Long miembroId);

    /**
     * Obtener todos los miembros que están actualmente dentro (accesos activos)
     */
    @Query("SELECT a FROM AccesoBiblioteca a WHERE a.estado = true AND a.fechaSalida IS NULL ORDER BY a.fechaEntrada DESC")
    List<AccesoBiblioteca> findMiembrosActivos();

    /**
     * Obtener historial de accesos de un miembro ordenado por fecha descendente
     */
    List<AccesoBiblioteca> findByMiembroIdOrderByFechaEntradaDesc(Long miembroId);

    /**
     * Obtener accesos por rango de fechas de entrada
     */
    List<AccesoBiblioteca> findByFechaEntradaBetweenOrderByFechaEntradaDesc(
        java.time.LocalDateTime fechaInicio, 
        java.time.LocalDateTime fechaFin
    );

    /**
     * Contar miembros actualmente dentro
     */
    @Query("SELECT COUNT(a) FROM AccesoBiblioteca a WHERE a.estado = true AND a.fechaSalida IS NULL")
    Long contarMiembrosAdentro();

    /**
     * Buscar por código QR
     */
    Optional<AccesoBiblioteca> findByCodigoQr(String codigoQr);
}