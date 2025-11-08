package com.biblioteca.web.controller;

import com.biblioteca.domain.service.AccesoBibliotecaService;
import com.biblioteca.web.dto.AccesoBibliotecaRequestDTO;
import com.biblioteca.web.dto.AccesoBibliotecaResponseDTO;
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
}