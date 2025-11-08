package com.biblioteca.domain.service;

import com.biblioteca.domain.model.AccesoBiblioteca;
import com.biblioteca.domain.repository.AccesoBibliotecaRepository;
import com.biblioteca.web.dto.AccesoBibliotecaRequestDTO;
import com.biblioteca.web.dto.AccesoBibliotecaResponseDTO;
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
 * Servicio de negocio para AccesoBiblioteca
 * Generado automáticamente desde diagrama UML
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccesoBibliotecaService {

    private final AccesoBibliotecaRepository accesoBibliotecaRepository;

    /**
     * Crear nueva entidad AccesoBiblioteca
     */
    public AccesoBibliotecaResponseDTO create(AccesoBibliotecaRequestDTO requestDTO) {
        log.info("Creando nueva entidad AccesoBiblioteca: {}", requestDTO);

        AccesoBiblioteca entity = new AccesoBiblioteca();
        mapRequestToEntity(requestDTO, entity);

        AccesoBiblioteca savedEntity = accesoBibliotecaRepository.save(entity);

        log.info("Entidad AccesoBiblioteca creada con ID: {}", savedEntity.getId());
        return mapEntityToResponse(savedEntity);
    }

    /**
     * Obtener entidad por ID
     */
    @Transactional(readOnly = true)
    public AccesoBibliotecaResponseDTO findById(Long id) {
        log.debug("Buscando AccesoBiblioteca con ID: {}", id);

        AccesoBiblioteca entity = accesoBibliotecaRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("AccesoBiblioteca", id));

        return mapEntityToResponse(entity);
    }

    /**
     * Obtener todas las entidades
     */
    @Transactional(readOnly = true)
    public List<AccesoBibliotecaResponseDTO> findAll() {
        log.debug("Obteniendo todas las entidades AccesoBiblioteca");

        return accesoBibliotecaRepository.findByOrderByCreatedAtDesc()
            .stream()
            .map(this::mapEntityToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Actualizar entidad existente
     */
    public AccesoBibliotecaResponseDTO update(Long id, AccesoBibliotecaRequestDTO requestDTO) {
        log.info("Actualizando AccesoBiblioteca con ID: {}", id);

        AccesoBiblioteca entity = accesoBibliotecaRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("AccesoBiblioteca", id));

        mapRequestToEntity(requestDTO, entity);
        AccesoBiblioteca updatedEntity = accesoBibliotecaRepository.save(entity);

        log.info("Entidad AccesoBiblioteca actualizada: {}", updatedEntity.getId());
        return mapEntityToResponse(updatedEntity);
    }

    /**
     * Eliminar entidad (soft delete)
     */
    public void delete(Long id) {
        log.info("Eliminando AccesoBiblioteca con ID: {}", id);

        AccesoBiblioteca entity = accesoBibliotecaRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("AccesoBiblioteca", id));

        // Implementar soft delete si es necesario
        accesoBibliotecaRepository.delete(entity);

        log.info("Entidad AccesoBiblioteca eliminada: {}", id);
    }

    // ==================== MÉTODOS DE MAPEO ====================

    private void mapRequestToEntity(AccesoBibliotecaRequestDTO requestDTO, AccesoBiblioteca entity) {
        // TODO: Implementar mapeo específico de campos
        // Ejemplo básico:
                if (requestDTO.getFechaEntrada() != null) {
            entity.setFechaEntrada(requestDTO.getFechaEntrada());
        }
        if (requestDTO.getFechaSalida() != null) {
            entity.setFechaSalida(requestDTO.getFechaSalida());
        }
        if (requestDTO.getMetodoAcceso() != null) {
            entity.setMetodoAcceso(requestDTO.getMetodoAcceso().trim());
        }
        if (requestDTO.getCodigoQr() != null) {
            entity.setCodigoQr(requestDTO.getCodigoQr().trim());
        }
        if (requestDTO.getEstado() != null) {
            entity.setEstado(requestDTO.getEstado());
        }
    }

    private AccesoBibliotecaResponseDTO mapEntityToResponse(AccesoBiblioteca entity) {
        AccesoBibliotecaResponseDTO responseDTO = new AccesoBibliotecaResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setCreatedAt(entity.getCreatedAt());
        responseDTO.setUpdatedAt(entity.getUpdatedAt());

        // TODO: Implementar mapeo específico de campos
        // Ejemplo básico:
                responseDTO.setFechaEntrada(entity.getFechaEntrada());
        responseDTO.setFechaSalida(entity.getFechaSalida());
        responseDTO.setMetodoAcceso(entity.getMetodoAcceso());
        responseDTO.setCodigoQr(entity.getCodigoQr());
        responseDTO.setEstado(entity.getEstado());

        return responseDTO;
    }
}