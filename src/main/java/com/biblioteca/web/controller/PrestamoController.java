package com.biblioteca.web.controller;

import com.biblioteca.domain.service.PrestamoService;
import com.biblioteca.web.dto.PrestamoRequestDTO;
import com.biblioteca.web.dto.PrestamoResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador REST para Prestamo
 * Generado automáticamente desde diagrama UML
 */
@RestController
@RequestMapping("/api/prestamo")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class PrestamoController {

    private final PrestamoService prestamoService;

    /**
     * Crear nuevo Prestamo
     */
    @PostMapping
    public ResponseEntity<PrestamoResponseDTO> create(@Valid @RequestBody PrestamoRequestDTO requestDTO) {
        log.info("REST: Creando Prestamo: {}", requestDTO);

        PrestamoResponseDTO responseDTO = prestamoService.create(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Obtener Prestamo por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> findById(@PathVariable Long id) {
        log.debug("REST: Obteniendo Prestamo con ID: {}", id);

        PrestamoResponseDTO responseDTO = prestamoService.findById(id);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Obtener todos los Prestamo
     */
    @GetMapping
    public ResponseEntity<List<PrestamoResponseDTO>> findAll() {
        log.debug("REST: Obteniendo todos los Prestamo");

        List<PrestamoResponseDTO> responseDTOs = prestamoService.findAll();

        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Actualizar Prestamo existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PrestamoRequestDTO requestDTO) {
        log.info("REST: Actualizando Prestamo con ID: {}", id);

        PrestamoResponseDTO responseDTO = prestamoService.update(id, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Eliminar Prestamo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST: Eliminando Prestamo con ID: {}", id);

        prestamoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}