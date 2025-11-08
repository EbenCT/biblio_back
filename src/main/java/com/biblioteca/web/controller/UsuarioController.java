package com.biblioteca.web.controller;

import com.biblioteca.domain.service.UsuarioService;
import com.biblioteca.web.dto.UsuarioRequestDTO;
import com.biblioteca.web.dto.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador REST para Usuario
 * Generado automáticamente desde diagrama UML
 */
@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Crear nuevo Usuario
     */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioRequestDTO requestDTO) {
        log.info("REST: Creando Usuario: {}", requestDTO);

        UsuarioResponseDTO responseDTO = usuarioService.create(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Obtener Usuario por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {
        log.debug("REST: Obteniendo Usuario con ID: {}", id);

        UsuarioResponseDTO responseDTO = usuarioService.findById(id);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Obtener todos los Usuario
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        log.debug("REST: Obteniendo todos los Usuario");

        List<UsuarioResponseDTO> responseDTOs = usuarioService.findAll();

        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Actualizar Usuario existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO requestDTO) {
        log.info("REST: Actualizando Usuario con ID: {}", id);

        UsuarioResponseDTO responseDTO = usuarioService.update(id, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Eliminar Usuario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST: Eliminando Usuario con ID: {}", id);

        usuarioService.delete(id);

        return ResponseEntity.noContent().build();
    }
}