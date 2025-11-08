package com.biblioteca.web.controller;

import com.biblioteca.domain.service.LibroService;
import com.biblioteca.web.dto.LibroRequestDTO;
import com.biblioteca.web.dto.LibroResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador REST para Libro
 * Generado automáticamente desde diagrama UML
 */
@RestController
@RequestMapping("/api/libro")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class LibroController {

    private final LibroService libroService;

    /**
     * Crear nuevo Libro
     */
    @PostMapping
    public ResponseEntity<LibroResponseDTO> create(@Valid @RequestBody LibroRequestDTO requestDTO) {
        log.info("REST: Creando Libro: {}", requestDTO);

        LibroResponseDTO responseDTO = libroService.create(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Obtener Libro por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> findById(@PathVariable Long id) {
        log.debug("REST: Obteniendo Libro con ID: {}", id);

        LibroResponseDTO responseDTO = libroService.findById(id);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Obtener todos los Libro
     */
    @GetMapping
    public ResponseEntity<List<LibroResponseDTO>> findAll() {
        log.debug("REST: Obteniendo todos los Libro");

        List<LibroResponseDTO> responseDTOs = libroService.findAll();

        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Actualizar Libro existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody LibroRequestDTO requestDTO) {
        log.info("REST: Actualizando Libro con ID: {}", id);

        LibroResponseDTO responseDTO = libroService.update(id, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Eliminar Libro
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST: Eliminando Libro con ID: {}", id);

        libroService.delete(id);

        return ResponseEntity.noContent().build();
    }
}