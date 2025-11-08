package com.biblioteca.domain.service;

import com.biblioteca.domain.model.Usuario;
import com.biblioteca.domain.repository.UsuarioRepository;
import com.biblioteca.web.dto.UsuarioRequestDTO;
import com.biblioteca.web.dto.UsuarioResponseDTO;
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
 * Servicio de negocio para Usuario
 * Generado automáticamente desde diagrama UML
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Crear nueva entidad Usuario
     */
    public UsuarioResponseDTO create(UsuarioRequestDTO requestDTO) {
        log.info("Creando nueva entidad Usuario: {}", requestDTO);

        Usuario entity = new Usuario();
        mapRequestToEntity(requestDTO, entity);

        Usuario savedEntity = usuarioRepository.save(entity);

        log.info("Entidad Usuario creada con ID: {}", savedEntity.getId());
        return mapEntityToResponse(savedEntity);
    }

    /**
     * Obtener entidad por ID
     */
    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(Long id) {
        log.debug("Buscando Usuario con ID: {}", id);

        Usuario entity = usuarioRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("Usuario", id));

        return mapEntityToResponse(entity);
    }

    /**
     * Obtener todas las entidades
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAll() {
        log.debug("Obteniendo todas las entidades Usuario");

        return usuarioRepository.findByOrderByCreatedAtDesc()
            .stream()
            .map(this::mapEntityToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Actualizar entidad existente
     */
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO requestDTO) {
        log.info("Actualizando Usuario con ID: {}", id);

        Usuario entity = usuarioRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("Usuario", id));

        mapRequestToEntity(requestDTO, entity);
        Usuario updatedEntity = usuarioRepository.save(entity);

        log.info("Entidad Usuario actualizada: {}", updatedEntity.getId());
        return mapEntityToResponse(updatedEntity);
    }

    /**
     * Eliminar entidad (soft delete)
     */
    public void delete(Long id) {
        log.info("Eliminando Usuario con ID: {}", id);

        Usuario entity = usuarioRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("Usuario", id));

        // Implementar soft delete si es necesario
        usuarioRepository.delete(entity);

        log.info("Entidad Usuario eliminada: {}", id);
    }

    // ==================== MÉTODOS DE MAPEO ====================

    private void mapRequestToEntity(UsuarioRequestDTO requestDTO, Usuario entity) {
        // TODO: Implementar mapeo específico de campos
        // Ejemplo básico:
                if (requestDTO.getEmail() != null) {
            entity.setEmail(requestDTO.getEmail().trim());
        }
        if (requestDTO.getPassword() != null) {
            entity.setPassword(requestDTO.getPassword().trim());
        }
    }

    private UsuarioResponseDTO mapEntityToResponse(Usuario entity) {
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setCreatedAt(entity.getCreatedAt());
        responseDTO.setUpdatedAt(entity.getUpdatedAt());

        // TODO: Implementar mapeo específico de campos
        // Ejemplo básico:
                responseDTO.setEmail(entity.getEmail());
        responseDTO.setPassword(entity.getPassword());

        return responseDTO;
    }
}