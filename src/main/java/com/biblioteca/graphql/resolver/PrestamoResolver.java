package com.biblioteca.graphql.resolver;

import com.biblioteca.domain.service.PrestamoService;
import com.biblioteca.web.dto.PrestamoRequestDTO;
import com.biblioteca.web.dto.PrestamoResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GraphQL Resolver para Prestamo
 * Maneja todas las operaciones CRUD de prestamos via GraphQL
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class PrestamoResolver {

    private final PrestamoService prestamoService;

    // ==================== QUERIES ====================

    /**
     * Obtener todos los prestamos
     */
    @QueryMapping
    public List<PrestamoResponseDTO> prestamos() {
        log.debug("GraphQL: Obteniendo todos los prestamos");
        return prestamoService.findAll();
    }

    /**
     * Obtener prestamo por ID
     */
    @QueryMapping
    public PrestamoResponseDTO prestamo(@Argument String id) {
        log.debug("GraphQL: Obteniendo prestamo con ID: {}", id);
        Long idLong = Long.parseLong(id);
        return prestamoService.findById(idLong);
    }

    /**
     * Obtener prestamos activos (no devueltos)
     */
    @QueryMapping
    public List<PrestamoResponseDTO> prestamosActivos() {
        log.debug("GraphQL: Obteniendo prestamos activos");
        return prestamoService.findAll().stream()
                .filter(prestamo -> prestamo.getEstado()) // estado = true significa activo
                .collect(Collectors.toList());
    }

    /**
     * Obtener prestamos vencidos
     */
    @QueryMapping
    public List<PrestamoResponseDTO> prestamosVencidos() {
        log.debug("GraphQL: Obteniendo prestamos vencidos");
        LocalDateTime ahora = LocalDateTime.now();
        return prestamoService.findAll().stream()
                .filter(prestamo -> prestamo.getEstado() && 
                       prestamo.getFechaDevolucionEsperada() != null &&
                       prestamo.getFechaDevolucionEsperada().isBefore(ahora))
                .collect(Collectors.toList());
    }

    /**
     * Obtener prestamos por estado de multa
     */
    @QueryMapping
    public List<PrestamoResponseDTO> prestamosConMulta() {
        log.debug("GraphQL: Obteniendo prestamos con multa");
        return prestamoService.findAll().stream()
                .filter(prestamo -> prestamo.getMulta() != null && prestamo.getMulta() > 0)
                .collect(Collectors.toList());
    }

    // ==================== MUTATIONS ====================

    /**
     * Crear nuevo prestamo
     */
    @MutationMapping
    public PrestamoResponseDTO crearPrestamo(@Argument PrestamoInput input) {
        log.info("GraphQL: Creando nuevo prestamo: {}", input);
        
        // Convertimos el input GraphQL al DTO REST existente
        PrestamoRequestDTO requestDTO = new PrestamoRequestDTO(
                input.getFechaPrestamo(),
                input.getFechaDevolucionEsperada(),
                input.getFechaDevolucionReal(),
                input.getEstado(),
                input.getMulta(),
                input.getLibroId(),
                input.getMiembroId()
        );
        
        return prestamoService.create(requestDTO);
    }

    /**
     * Actualizar prestamo existente
     */
    @MutationMapping
    public PrestamoResponseDTO actualizarPrestamo(@Argument String id, @Argument PrestamoInput input) {
        log.info("GraphQL: Actualizando prestamo con ID: {}", id);
        
        Long idLong = Long.parseLong(id);
        PrestamoRequestDTO requestDTO = new PrestamoRequestDTO(
                input.getFechaPrestamo(),
                input.getFechaDevolucionEsperada(),
                input.getFechaDevolucionReal(),
                input.getEstado(),
                input.getMulta(),
                input.getLibroId(),
                input.getMiembroId()
        );
        
        return prestamoService.update(idLong, requestDTO);
    }

    /**
     * Eliminar prestamo
     */
    @MutationMapping
    public Boolean eliminarPrestamo(@Argument String id) {
        log.info("GraphQL: Eliminando prestamo con ID: {}", id);
        try {
            Long idLong = Long.parseLong(id);
            prestamoService.delete(idLong);
            return true;
        } catch (Exception e) {
            log.error("Error eliminando prestamo: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Marcar prestamo como devuelto
     */
    @MutationMapping
    public PrestamoResponseDTO marcarComoDevuelto(@Argument String id) {
        log.info("GraphQL: Marcando prestamo como devuelto - ID: {}", id);
        
        try {
            Long idLong = Long.parseLong(id);
            PrestamoResponseDTO prestamo = prestamoService.findById(idLong);
            
            // Actualizar con fecha de devolución real y estado inactivo
            PrestamoRequestDTO updateDTO = new PrestamoRequestDTO(
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucionEsperada(),
                    LocalDateTime.now(), // Fecha de devolución real = ahora
                    false, // estado = false (devuelto)
                    prestamo.getMulta()
            );
            // Mantener las relaciones existentes
            updateDTO.setLibroId(prestamo.getLibroId());
            updateDTO.setMiembroId(prestamo.getMiembroId());
            
            return prestamoService.update(idLong, updateDTO);
        } catch (Exception e) {
            log.error("Error marcando prestamo como devuelto: {}", e.getMessage());
            throw new RuntimeException("Error al marcar prestamo como devuelto: " + e.getMessage());
        }
    }

    /**
     * Aplicar multa a un prestamo
     */
    @MutationMapping
    public PrestamoResponseDTO aplicarMulta(@Argument String id, @Argument Double montoMulta) {
        log.info("GraphQL: Aplicando multa de {} al prestamo ID: {}", montoMulta, id);
        
        try {
            Long idLong = Long.parseLong(id);
            PrestamoResponseDTO prestamo = prestamoService.findById(idLong);
            
            // Actualizar solo el monto de la multa
            PrestamoRequestDTO updateDTO = new PrestamoRequestDTO(
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucionEsperada(),
                    prestamo.getFechaDevolucionReal(),
                    prestamo.getEstado(),
                    montoMulta // Nueva multa
            );
            // Mantener las relaciones existentes
            updateDTO.setLibroId(prestamo.getLibroId());
            updateDTO.setMiembroId(prestamo.getMiembroId());
            
            return prestamoService.update(idLong, updateDTO);
        } catch (Exception e) {
            log.error("Error aplicando multa: {}", e.getMessage());
            throw new RuntimeException("Error al aplicar multa: " + e.getMessage());
        }
    }

    // Clase interna para el input GraphQL
    public static class PrestamoInput {
        private LocalDateTime fechaPrestamo;
        private LocalDateTime fechaDevolucionEsperada;
        private LocalDateTime fechaDevolucionReal;
        private Boolean estado;
        private Double multa;
        private Long libroId;
        private Long miembroId;

        // Getters y setters
        public LocalDateTime getFechaPrestamo() { return fechaPrestamo; }
        public void setFechaPrestamo(LocalDateTime fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
        
        public LocalDateTime getFechaDevolucionEsperada() { return fechaDevolucionEsperada; }
        public void setFechaDevolucionEsperada(LocalDateTime fechaDevolucionEsperada) { 
            this.fechaDevolucionEsperada = fechaDevolucionEsperada; 
        }
        
        public LocalDateTime getFechaDevolucionReal() { return fechaDevolucionReal; }
        public void setFechaDevolucionReal(LocalDateTime fechaDevolucionReal) { 
            this.fechaDevolucionReal = fechaDevolucionReal; 
        }
        
        public Boolean getEstado() { return estado; }
        public void setEstado(Boolean estado) { this.estado = estado; }
        
        public Double getMulta() { return multa; }
        public void setMulta(Double multa) { this.multa = multa; }
        
        public Long getLibroId() { return libroId; }
        public void setLibroId(Long libroId) { this.libroId = libroId; }
        
        public Long getMiembroId() { return miembroId; }
        public void setMiembroId(Long miembroId) { this.miembroId = miembroId; }

        @Override
        public String toString() {
            return String.format("PrestamoInput{fechaPrestamo='%s', fechaDevolucionEsperada='%s', " +
                    "fechaDevolucionReal='%s', estado=%s, multa=%s, libroId=%s, miembroId=%s}", 
                    fechaPrestamo, fechaDevolucionEsperada, fechaDevolucionReal, estado, multa, libroId, miembroId);
        }
    }
}