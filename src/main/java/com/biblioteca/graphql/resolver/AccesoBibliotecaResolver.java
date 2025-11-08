package com.biblioteca.graphql.resolver;

import com.biblioteca.domain.service.AccesoBibliotecaService;
import com.biblioteca.web.dto.AccesoBibliotecaRequestDTO;
import com.biblioteca.web.dto.AccesoBibliotecaResponseDTO;
import com.biblioteca.web.dto.IngresoEgresoResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GraphQL Resolver para AccesoBiblioteca
 * Maneja todas las operaciones de ingreso/egreso via GraphQL
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class AccesoBibliotecaResolver {

    private final AccesoBibliotecaService accesoBibliotecaService;

    // ==================== QUERIES ====================

    /**
     * Obtener todos los accesos
     */
    @QueryMapping
    public List<AccesoBibliotecaResponseDTO> accesos() {
        log.debug("GraphQL: Obteniendo todos los accesos");
        return accesoBibliotecaService.findAll();
    }

    /**
     * Obtener acceso por ID
     */
    @QueryMapping
    public AccesoBibliotecaResponseDTO acceso(@Argument String id) {
        log.debug("GraphQL: Obteniendo acceso con ID: {}", id);
        Long idLong = Long.parseLong(id);
        return accesoBibliotecaService.findById(idLong);
    }

    /**
     * Verificar si un miembro está dentro de la biblioteca
     */
    @QueryMapping
    public Boolean miembroEstaAdentro(@Argument String miembroId) {
        log.debug("GraphQL: Verificando si miembro {} está adentro", miembroId);
        Long miembroIdLong = Long.parseLong(miembroId);
        return accesoBibliotecaService.estaAdentro(miembroIdLong);
    }

    /**
     * Obtener miembros actualmente dentro de la biblioteca
     */
    @QueryMapping
    public List<AccesoBibliotecaResponseDTO> miembrosAdentro() {
        log.debug("GraphQL: Obteniendo miembros actualmente dentro");
        return accesoBibliotecaService.obtenerMiembrosAdentro();
    }

    /**
     * Obtener historial de accesos de un miembro
     */
    @QueryMapping
    public List<AccesoBibliotecaResponseDTO> historialMiembro(@Argument String miembroId) {
        log.debug("GraphQL: Obteniendo historial de accesos para miembro {}", miembroId);
        Long miembroIdLong = Long.parseLong(miembroId);
        return accesoBibliotecaService.obtenerHistorialMiembro(miembroIdLong);
    }

    /**
     * Obtener reporte de accesos por fechas
     */
    @QueryMapping
    public List<AccesoBibliotecaResponseDTO> reporteAccesos(
            @Argument String fechaInicio,
            @Argument String fechaFin) {
        log.debug("GraphQL: Obteniendo reporte desde {} hasta {}", fechaInicio, fechaFin);
        
        try {
            LocalDateTime inicio = LocalDateTime.parse(fechaInicio);
            LocalDateTime fin = LocalDateTime.parse(fechaFin);
            return accesoBibliotecaService.obtenerReportePorFechas(inicio, fin);
        } catch (Exception e) {
            log.error("Error parsing dates: {}", e.getMessage());
            throw new IllegalArgumentException("Formato de fecha inválido. Use: yyyy-MM-ddTHH:mm:ss");
        }
    }

    // ==================== MUTATIONS ====================

    /**
     * Crear nuevo acceso (CRUD básico)
     */
    @MutationMapping
    public AccesoBibliotecaResponseDTO crearAcceso(@Argument AccesoBibliotecaRequestDTO input) {
        log.info("GraphQL: Creando nuevo acceso: {}", input);
        return accesoBibliotecaService.create(input);
    }

    /**
     * Actualizar acceso existente (CRUD básico)
     */
    @MutationMapping
    public AccesoBibliotecaResponseDTO actualizarAcceso(@Argument String id, @Argument AccesoBibliotecaRequestDTO input) {
        log.info("GraphQL: Actualizando acceso con ID: {}", id);
        Long idLong = Long.parseLong(id);
        return accesoBibliotecaService.update(idLong, input);
    }

    /**
     * Eliminar acceso (CRUD básico)
     */
    @MutationMapping
    public Boolean eliminarAcceso(@Argument String id) {
        log.info("GraphQL: Eliminando acceso con ID: {}", id);
        try {
            Long idLong = Long.parseLong(id);
            accesoBibliotecaService.delete(idLong);
            return true;
        } catch (Exception e) {
            log.error("Error al eliminar acceso: {}", e.getMessage());
            return false;
        }
    }

    // ==================== MUTATIONS ESPECÍFICAS PARA MÓVIL ====================

    /**
     * Generar código QR para escaneo
     * Usado por la web administrativa
     */
    @MutationMapping
    public String generarCodigoQR() {
        log.info("GraphQL: Generando código QR");
        return accesoBibliotecaService.generarCodigoQR();
    }

    /**
     * Registrar INGRESO escaneando QR
     * Llamado desde la app móvil Flutter
     */
    @MutationMapping
    public IngresoEgresoResponseDTO registrarIngreso(@Argument String miembroId, @Argument String codigoQr) {
        log.info("GraphQL: Registrando ingreso - MiembroId: {}, CodigoQR: {}", miembroId, codigoQr);
        
        try {
            // Convertir String a Long para el servicio
            Long miembroIdLong = Long.parseLong(miembroId);
            AccesoBibliotecaResponseDTO acceso = accesoBibliotecaService.registrarIngreso(miembroIdLong, codigoQr);
            return IngresoEgresoResponseDTO.ingresoExitoso(acceso);
        } catch (NumberFormatException e) {
            log.error("ID de miembro inválido: {}", miembroId, e);
            return IngresoEgresoResponseDTO.error("INGRESO", "ID de miembro inválido");
        } catch (IllegalStateException e) {
            log.warn("Error al registrar ingreso: {}", e.getMessage());
            return IngresoEgresoResponseDTO.error("INGRESO", e.getMessage());
        }
    }

    /**
     * Registrar EGRESO escaneando QR
     * Llamado desde la app móvil Flutter
     */
    @MutationMapping
    public IngresoEgresoResponseDTO registrarEgreso(@Argument String miembroId, @Argument String codigoQr) {
        log.info("GraphQL: Registrando egreso - MiembroId: {}, CodigoQR: {}", miembroId, codigoQr);
        
        try {
            // Convertir String a Long para el servicio
            Long miembroIdLong = Long.parseLong(miembroId);
            AccesoBibliotecaResponseDTO acceso = accesoBibliotecaService.registrarEgreso(miembroIdLong, codigoQr);
            return IngresoEgresoResponseDTO.egresoExitoso(acceso);
        } catch (NumberFormatException e) {
            log.error("ID de miembro inválido: {}", miembroId, e);
            return IngresoEgresoResponseDTO.error("EGRESO", "ID de miembro inválido");
        } catch (IllegalStateException e) {
            log.warn("Error al registrar egreso: {}", e.getMessage());
            return IngresoEgresoResponseDTO.error("EGRESO", e.getMessage());
        }
    }
}