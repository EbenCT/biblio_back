package com.biblioteca.domain.service;

import com.biblioteca.domain.model.Libro;
import com.biblioteca.domain.repository.LibroRepository;
import com.biblioteca.web.dto.LibroRequestDTO;
import com.biblioteca.web.dto.LibroResponseDTO;
import com.biblioteca.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de negocio para Libro
 * Generado automáticamente desde diagrama UML
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LibroService {

    private final LibroRepository libroRepository;

    /**
     * Crear nueva entidad Libro
     */
    public LibroResponseDTO create(LibroRequestDTO requestDTO) {
        log.info("Creando nueva entidad Libro: {}", requestDTO);

        Libro entity = new Libro();
        mapRequestToEntity(requestDTO, entity);

        Libro savedEntity = libroRepository.save(entity);

        log.info("Entidad Libro creada con ID: {}", savedEntity.getId());
        return mapEntityToResponse(savedEntity);
    }

    /**
     * Obtener entidad por ID
     */
    @Transactional(readOnly = true)
    public LibroResponseDTO findById(Long id) {
        log.debug("Buscando Libro con ID: {}", id);

        Libro entity = libroRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("Libro", id));

        return mapEntityToResponse(entity);
    }

    /**
     * Obtener todas las entidades
     */
    @Transactional(readOnly = true)
    public List<LibroResponseDTO> findAll() {
        log.debug("Obteniendo todas las entidades Libro");

        return libroRepository.findByOrderByCreatedAtDesc()
            .stream()
            .map(this::mapEntityToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Actualizar entidad existente
     */
    public LibroResponseDTO update(Long id, LibroRequestDTO requestDTO) {
        log.info("Actualizando Libro con ID: {}", id);

        Libro entity = libroRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("Libro", id));

        mapRequestToEntity(requestDTO, entity);
        Libro updatedEntity = libroRepository.save(entity);

        log.info("Entidad Libro actualizada: {}", updatedEntity.getId());
        return mapEntityToResponse(updatedEntity);
    }

    /**
     * Eliminar entidad (soft delete)
     */
    public void delete(Long id) {
        log.info("Eliminando Libro con ID: {}", id);

        Libro entity = libroRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("Libro", id));

        // Implementar soft delete si es necesario
        libroRepository.delete(entity);

        log.info("Entidad Libro eliminada: {}", id);
    }

    // ==================== MÉTODOS DE MAPEO ====================

    private void mapRequestToEntity(LibroRequestDTO requestDTO, Libro entity) {
        // TODO: Implementar mapeo específico de campos
        // Ejemplo básico:
                if (requestDTO.getTitulo() != null) {
            entity.setTitulo(requestDTO.getTitulo().trim());
        }
        if (requestDTO.getAutor() != null) {
            entity.setAutor(requestDTO.getAutor().trim());
        }
        if (requestDTO.getCategoria() != null) {
            entity.setCategoria(requestDTO.getCategoria().trim());
        }
        if (requestDTO.getDisponible() != null) {
            entity.setDisponible(requestDTO.getDisponible());
        }
    }

    private LibroResponseDTO mapEntityToResponse(Libro entity) {
        LibroResponseDTO responseDTO = new LibroResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setCreatedAt(entity.getCreatedAt());
        responseDTO.setUpdatedAt(entity.getUpdatedAt());

        // TODO: Implementar mapeo específico de campos
        // Ejemplo básico:
                responseDTO.setTitulo(entity.getTitulo());
        responseDTO.setAutor(entity.getAutor());
        responseDTO.setCategoria(entity.getCategoria());
        responseDTO.setDisponible(entity.getDisponible());

        return responseDTO;
    }
}