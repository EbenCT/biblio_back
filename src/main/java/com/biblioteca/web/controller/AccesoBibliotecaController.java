package com.biblioteca.web.controller;

import com.biblioteca.domain.service.AccesoBibliotecaService;
import com.biblioteca.web.dto.AccesoBibliotecaRequestDTO;
import com.biblioteca.web.dto.AccesoBibliotecaResponseDTO;
import com.biblioteca.web.dto.IngresoEgresoResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador REST para AccesoBiblioteca
 * Generado automáticamente desde diagrama UML
 */
@RestController
@RequestMapping("/api/acceso-biblioteca")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class AccesoBibliotecaController {

    private final AccesoBibliotecaService accesoBibliotecaService;

    /**
     * Crear nuevo AccesoBiblioteca
     */
    @PostMapping
    public ResponseEntity<AccesoBibliotecaResponseDTO> create(@Valid @RequestBody AccesoBibliotecaRequestDTO requestDTO) {
        log.info("REST: Creando AccesoBiblioteca: {}", requestDTO);

        AccesoBibliotecaResponseDTO responseDTO = accesoBibliotecaService.create(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Obtener AccesoBiblioteca por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccesoBibliotecaResponseDTO> findById(@PathVariable Long id) {
        log.debug("REST: Obteniendo AccesoBiblioteca con ID: {}", id);

        AccesoBibliotecaResponseDTO responseDTO = accesoBibliotecaService.findById(id);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Obtener todos los AccesoBiblioteca
     */
    @GetMapping
    public ResponseEntity<List<AccesoBibliotecaResponseDTO>> findAll() {
        log.debug("REST: Obteniendo todos los AccesoBiblioteca");

        List<AccesoBibliotecaResponseDTO> responseDTOs = accesoBibliotecaService.findAll();

        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Actualizar AccesoBiblioteca existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<AccesoBibliotecaResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AccesoBibliotecaRequestDTO requestDTO) {
        log.info("REST: Actualizando AccesoBiblioteca con ID: {}", id);

        AccesoBibliotecaResponseDTO responseDTO = accesoBibliotecaService.update(id, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Eliminar AccesoBiblioteca
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST: Eliminando AccesoBiblioteca con ID: {}", id);

        accesoBibliotecaService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // ==================== ENDPOINTS ESPECÍFICOS PARA INGRESO/EGRESO ====================

    /**
     * Generar código QR para escaneo
     * Usado por la web administrativa para generar QR visual
     */
    @PostMapping("/generar-qr")
    public ResponseEntity<String> generarCodigoQR() {
        log.info("REST: Generando código QR");
        
        String codigoQR = accesoBibliotecaService.generarCodigoQR();
        
        return ResponseEntity.ok(codigoQR);
    }

    /**
     * Registrar INGRESO escaneando QR
     * Llamado desde la app móvil Flutter
     */
    @PostMapping("/ingreso")
    public ResponseEntity<IngresoEgresoResponseDTO> registrarIngreso(
            @RequestParam Long miembroId,
            @RequestParam String codigoQr) {
        log.info("REST: Registrando ingreso - MiembroId: {}, CodigoQR: {}", miembroId, codigoQr);
        
        try {
            AccesoBibliotecaResponseDTO acceso = accesoBibliotecaService.registrarIngreso(miembroId, codigoQr);
            IngresoEgresoResponseDTO response = IngresoEgresoResponseDTO.ingresoExitoso(acceso);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            log.warn("Error al registrar ingreso: {}", e.getMessage());
            IngresoEgresoResponseDTO errorResponse = IngresoEgresoResponseDTO.error("INGRESO", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * Registrar EGRESO escaneando QR  
     * Llamado desde la app móvil Flutter
     */
    @PostMapping("/egreso")
    public ResponseEntity<IngresoEgresoResponseDTO> registrarEgreso(
            @RequestParam Long miembroId,
            @RequestParam String codigoQr) {
        log.info("REST: Registrando egreso - MiembroId: {}, CodigoQR: {}", miembroId, codigoQr);
        
        try {
            AccesoBibliotecaResponseDTO acceso = accesoBibliotecaService.registrarEgreso(miembroId, codigoQr);
            IngresoEgresoResponseDTO response = IngresoEgresoResponseDTO.egresoExitoso(acceso);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            log.warn("Error al registrar egreso: {}", e.getMessage());
            IngresoEgresoResponseDTO errorResponse = IngresoEgresoResponseDTO.error("EGRESO", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * Verificar si un miembro está dentro de la biblioteca
     * Para validaciones desde la app móvil
     */
    @GetMapping("/miembro/{miembroId}/esta-adentro")
    public ResponseEntity<Boolean> estaAdentro(@PathVariable Long miembroId) {
        log.debug("REST: Verificando si miembro {} está adentro", miembroId);
        
        boolean estaAdentro = accesoBibliotecaService.estaAdentro(miembroId);
        
        return ResponseEntity.ok(estaAdentro);
    }

    /**
     * Obtener miembros actualmente dentro de la biblioteca
     * Para el panel administrativo web
     */
    @GetMapping("/miembros-adentro")
    public ResponseEntity<List<AccesoBibliotecaResponseDTO>> obtenerMiembrosAdentro() {
        log.debug("REST: Obteniendo miembros actualmente dentro");
        
        List<AccesoBibliotecaResponseDTO> miembrosAdentro = accesoBibliotecaService.obtenerMiembrosAdentro();
        
        return ResponseEntity.ok(miembrosAdentro);
    }

    /**
     * Obtener historial de accesos de un miembro
     * Para la app móvil (funcionalidad futura)
     */
    @GetMapping("/miembro/{miembroId}/historial")
    public ResponseEntity<List<AccesoBibliotecaResponseDTO>> obtenerHistorialMiembro(@PathVariable Long miembroId) {
        log.debug("REST: Obteniendo historial de accesos para miembro {}", miembroId);
        
        List<AccesoBibliotecaResponseDTO> historial = accesoBibliotecaService.obtenerHistorialMiembro(miembroId);
        
        return ResponseEntity.ok(historial);
    }

    /**
     * Obtener reporte de accesos por fechas
     * Para el panel administrativo web
     */
    @GetMapping("/reporte")
    public ResponseEntity<List<AccesoBibliotecaResponseDTO>> obtenerReportePorFechas(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        log.debug("REST: Obteniendo reporte desde {} hasta {}", fechaInicio, fechaFin);
        
        try {
            java.time.LocalDateTime inicio = java.time.LocalDateTime.parse(fechaInicio);
            java.time.LocalDateTime fin = java.time.LocalDateTime.parse(fechaFin);
            
            List<AccesoBibliotecaResponseDTO> reporte = accesoBibliotecaService.obtenerReportePorFechas(inicio, fin);
            
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            log.error("Error al parsear fechas: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}