package com.biblioteca.web.controller;

import com.biblioteca.domain.service.MiembroService;
import com.biblioteca.web.dto.MiembroRequestDTO;
import com.biblioteca.web.dto.MiembroResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador REST para Miembro
 * Generado automáticamente desde diagrama UML
 */
@RestController
@RequestMapping("/api/miembro")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class MiembroController {

    private final MiembroService miembroService;

    /**
     * Crear nuevo Miembro
     */
    @PostMapping
    public ResponseEntity<MiembroResponseDTO> create(@Valid @RequestBody MiembroRequestDTO requestDTO) {
        log.info("REST: Creando Miembro: {}", requestDTO);

        MiembroResponseDTO responseDTO = miembroService.create(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Obtener Miembro por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MiembroResponseDTO> findById(@PathVariable Long id) {
        log.debug("REST: Obteniendo Miembro con ID: {}", id);

        MiembroResponseDTO responseDTO = miembroService.findById(id);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Obtener todos los Miembro
     */
    @GetMapping
    public ResponseEntity<List<MiembroResponseDTO>> findAll() {
        log.debug("REST: Obteniendo todos los Miembro");

        List<MiembroResponseDTO> responseDTOs = miembroService.findAll();

        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Actualizar Miembro existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<MiembroResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody MiembroRequestDTO requestDTO) {
        log.info("REST: Actualizando Miembro con ID: {}", id);

        MiembroResponseDTO responseDTO = miembroService.update(id, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Eliminar Miembro
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST: Eliminando Miembro con ID: {}", id);

        miembroService.delete(id);

        return ResponseEntity.noContent().build();
    }
}