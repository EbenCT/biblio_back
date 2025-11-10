package com.biblioteca.domain.service;

import com.biblioteca.domain.model.Prestamo;
import com.biblioteca.domain.model.Libro;
import com.biblioteca.domain.model.Miembro;
import com.biblioteca.domain.repository.PrestamoRepository;
import com.biblioteca.domain.repository.LibroRepository;
import com.biblioteca.domain.repository.MiembroRepository;
import com.biblioteca.web.dto.PrestamoRequestDTO;
import com.biblioteca.web.dto.PrestamoResponseDTO;
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
 * Servicio de negocio para Prestamo
 * Generado automáticamente desde diagrama UML
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final MiembroRepository miembroRepository;

    /**
     * Crear nueva entidad Prestamo
     */
    public PrestamoResponseDTO create(PrestamoRequestDTO requestDTO) {
        log.info("Creando nueva entidad Prestamo: {}", requestDTO);

        Prestamo entity = new Prestamo();
        mapRequestToEntity(requestDTO, entity);

        Prestamo savedEntity = prestamoRepository.save(entity);

        log.info("Entidad Prestamo creada con ID: {}", savedEntity.getId());
        return mapEntityToResponse(savedEntity);
    }

    /**
     * Obtener entidad por ID
     */
    @Transactional(readOnly = true)
    public PrestamoResponseDTO findById(Long id) {
        log.debug("Buscando Prestamo con ID: {}", id);

        Prestamo entity = prestamoRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("Prestamo", id));

        return mapEntityToResponse(entity);
    }

    /**
     * Obtener todas las entidades
     */
    @Transactional(readOnly = true)
    public List<PrestamoResponseDTO> findAll() {
        log.debug("Obteniendo todas las entidades Prestamo");

        return prestamoRepository.findByOrderByCreatedAtDesc()
            .stream()
            .map(this::mapEntityToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Actualizar entidad existente
     */
    public PrestamoResponseDTO update(Long id, PrestamoRequestDTO requestDTO) {
        log.info("Actualizando Prestamo con ID: {}", id);

        Prestamo entity = prestamoRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("Prestamo", id));

        mapRequestToEntity(requestDTO, entity);
        Prestamo updatedEntity = prestamoRepository.save(entity);

        log.info("Entidad Prestamo actualizada: {}", updatedEntity.getId());
        return mapEntityToResponse(updatedEntity);
    }

    /**
     * Eliminar entidad (soft delete)
     */
    public void delete(Long id) {
        log.info("Eliminando Prestamo con ID: {}", id);

        Prestamo entity = prestamoRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("Prestamo", id));

        // Implementar soft delete si es necesario
        prestamoRepository.delete(entity);

        log.info("Entidad Prestamo eliminada: {}", id);
    }

    // ==================== MÉTODOS DE MAPEO ====================

    private void mapRequestToEntity(PrestamoRequestDTO requestDTO, Prestamo entity) {
        // TODO: Implementar mapeo específico de campos
        // Ejemplo básico:
                if (requestDTO.getFechaPrestamo() != null) {
            entity.setFechaPrestamo(requestDTO.getFechaPrestamo());
        }
        if (requestDTO.getFechaDevolucionEsperada() != null) {
            entity.setFechaDevolucionEsperada(requestDTO.getFechaDevolucionEsperada());
        }
        if (requestDTO.getFechaDevolucionReal() != null) {
            entity.setFechaDevolucionReal(requestDTO.getFechaDevolucionReal());
        }
        if (requestDTO.getEstado() != null) {
            entity.setEstado(requestDTO.getEstado());
        }
        if (requestDTO.getMulta() != null) {
            entity.setMulta(requestDTO.getMulta());
        }
        
        // Mapear relaciones
        if (requestDTO.getLibroId() != null) {
            Libro libro = libroRepository.findById(requestDTO.getLibroId())
                .orElseThrow(() -> EntityNotFoundException.notFound("Libro", requestDTO.getLibroId()));
            entity.setLibro(libro);
        }
        
        if (requestDTO.getMiembroId() != null) {
            Miembro miembro = miembroRepository.findById(requestDTO.getMiembroId())
                .orElseThrow(() -> EntityNotFoundException.notFound("Miembro", requestDTO.getMiembroId()));
            entity.setMiembro(miembro);
        }
    }

    private PrestamoResponseDTO mapEntityToResponse(Prestamo entity) {
        PrestamoResponseDTO responseDTO = new PrestamoResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setCreatedAt(entity.getCreatedAt());
        responseDTO.setUpdatedAt(entity.getUpdatedAt());

        // TODO: Implementar mapeo específico de campos
        // Ejemplo básico:
                responseDTO.setFechaPrestamo(entity.getFechaPrestamo());
        responseDTO.setFechaDevolucionEsperada(entity.getFechaDevolucionEsperada());
        responseDTO.setFechaDevolucionReal(entity.getFechaDevolucionReal());
        responseDTO.setEstado(entity.getEstado());
        responseDTO.setMulta(entity.getMulta());
        
        // Mapear IDs de las entidades relacionadas
        if (entity.getLibro() != null) {
            responseDTO.setLibroId(entity.getLibro().getId());
        }
        if (entity.getMiembro() != null) {
            responseDTO.setMiembroId(entity.getMiembro().getId());
        }

        return responseDTO;
    }
}