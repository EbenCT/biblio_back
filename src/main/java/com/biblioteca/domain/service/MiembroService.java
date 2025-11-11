package com.biblioteca.domain.service;

import com.biblioteca.auth.dto.AuthResponseDTO;
import com.biblioteca.auth.dto.RegisterRequestDTO;
import com.biblioteca.auth.service.AuthService;
import com.biblioteca.domain.model.Miembro;
import com.biblioteca.domain.model.Usuario;
import com.biblioteca.domain.repository.MiembroRepository;
import com.biblioteca.domain.repository.UsuarioRepository;
import com.biblioteca.web.dto.MiembroRequestDTO;
import com.biblioteca.web.dto.MiembroResponseDTO;
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
 * Servicio de negocio para Miembro
 * Generado automáticamente desde diagrama UML
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MiembroService {

    private final MiembroRepository miembroRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthService authService;

    /**
     * Crear nuevo miembro con usuario y devolver AuthResponse (login automático)
     */
    public AuthResponseDTO createMiembroWithAuth(MiembroRequestDTO requestDTO) {
        log.info("Creando nuevo miembro con login automático: {}", requestDTO);

        if (requestDTO.getEmail() == null || requestDTO.getPassword() == null) {
            throw new RuntimeException("Email y password son requeridos para crear miembro con autenticación");
        }

        // 1. Crear usuario usando AuthService (cifra password y genera token)
        RegisterRequestDTO registerRequest = new RegisterRequestDTO();
        registerRequest.setEmail(requestDTO.getEmail().trim());
        registerRequest.setPassword(requestDTO.getPassword());
        
        AuthResponseDTO authResponse = authService.register(registerRequest);
        
        // 2. Crear miembro y asociarlo al usuario
        Miembro entity = new Miembro();
        mapRequestToEntity(requestDTO, entity);
        
        // Obtener el usuario creado por AuthService
        Usuario usuario = usuarioRepository.findByEmail(requestDTO.getEmail().trim())
            .orElseThrow(() -> new RuntimeException("Error al obtener usuario creado"));
        
        entity.setUsuario(usuario);
        
        Miembro savedEntity = miembroRepository.save(entity);
        
        log.info("Miembro creado con ID: {} y login automático exitoso", savedEntity.getId());
        
        return authResponse; // Devolver el token y datos de autenticación
    }

    /**
     * Crear nueva entidad Miembro (método original)
     */
    public MiembroResponseDTO create(MiembroRequestDTO requestDTO) {
        log.info("Creando nueva entidad Miembro: {}", requestDTO);

        Miembro entity = new Miembro();
        mapRequestToEntity(requestDTO, entity);

        // Si se proporcionan email y password, usar AuthService para crear usuario
        if (requestDTO.getEmail() != null && requestDTO.getPassword() != null) {
            log.info("Creando usuario usando AuthService para miembro: {}", requestDTO.getEmail());
            
            // Usar AuthService que ya maneja cifrado y validaciones
            RegisterRequestDTO registerRequest = new RegisterRequestDTO();
            registerRequest.setEmail(requestDTO.getEmail().trim());
            registerRequest.setPassword(requestDTO.getPassword());
            
            AuthResponseDTO authResponse = authService.register(registerRequest);
            
            // Obtener el usuario creado por AuthService
            Usuario usuario = usuarioRepository.findByEmail(requestDTO.getEmail().trim())
                .orElseThrow(() -> new RuntimeException("Error al obtener usuario creado"));
            
            entity.setUsuario(usuario);
            
            log.info("Usuario creado usando AuthService con ID: {}", usuario.getId());
        }

        Miembro savedEntity = miembroRepository.save(entity);

        log.info("Entidad Miembro creada con ID: {}", savedEntity.getId());
        return mapEntityToResponse(savedEntity);
    }

    /**
     * Obtener entidad por ID
     */
    @Transactional(readOnly = true)
    public MiembroResponseDTO findById(Long id) {
        log.debug("Buscando Miembro con ID: {}", id);

        Miembro entity = miembroRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("Miembro", id));

        return mapEntityToResponse(entity);
    }

    /**
     * Obtener miembro por userId
     */
    @Transactional(readOnly = true)
    public MiembroResponseDTO findByUsuarioId(Long userId) {
        log.debug("Buscando Miembro con userId: {}", userId);

        Miembro entity = miembroRepository.findByUsuarioId(userId)
            .orElseThrow(() -> new EntityNotFoundException("No se encontró miembro asociado al usuario con ID: " + userId));

        return mapEntityToResponse(entity);
    }

    /**
     * Obtener todas las entidades
     */
    @Transactional(readOnly = true)
    public List<MiembroResponseDTO> findAll() {
        log.debug("Obteniendo todas las entidades Miembro");

        return miembroRepository.findByOrderByCreatedAtDesc()
            .stream()
            .map(this::mapEntityToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Actualizar entidad existente
     */
    public MiembroResponseDTO update(Long id, MiembroRequestDTO requestDTO) {
        log.info("Actualizando Miembro con ID: {}", id);

        Miembro entity = miembroRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("Miembro", id));

        mapRequestToEntity(requestDTO, entity);
        Miembro updatedEntity = miembroRepository.save(entity);

        log.info("Entidad Miembro actualizada: {}", updatedEntity.getId());
        return mapEntityToResponse(updatedEntity);
    }

    /**
     * Eliminar entidad (soft delete)
     */
    public void delete(Long id) {
        log.info("Eliminando Miembro con ID: {}", id);

        Miembro entity = miembroRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.notFound("Miembro", id));

        // Implementar soft delete si es necesario
        miembroRepository.delete(entity);

        log.info("Entidad Miembro eliminada: {}", id);
    }

    // ==================== MÉTODOS DE MAPEO ====================

    private void mapRequestToEntity(MiembroRequestDTO requestDTO, Miembro entity) {
        // Mapeo de campos requeridos
        if (requestDTO.getNombre() != null) {
            entity.setNombre(requestDTO.getNombre().trim());
        }
        if (requestDTO.getApellido() != null) {
            entity.setApellido(requestDTO.getApellido().trim());
        }
        if (requestDTO.getDireccion() != null) {
            entity.setDireccion(requestDTO.getDireccion().trim());
        }
        if (requestDTO.getTelefono() != null) {
            entity.setTelefono(requestDTO.getTelefono().trim());
        }
        
        // Asignar fechaRegistro - si no viene en el request, usar fecha actual
        if (requestDTO.getFechaRegistro() != null) {
            entity.setFechaRegistro(requestDTO.getFechaRegistro());
        } else {
            entity.setFechaRegistro(LocalDateTime.now());
        }
    }

    private MiembroResponseDTO mapEntityToResponse(Miembro entity) {
        MiembroResponseDTO responseDTO = new MiembroResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setCreatedAt(entity.getCreatedAt());
        responseDTO.setUpdatedAt(entity.getUpdatedAt());

        // TODO: Implementar mapeo específico de campos
        // Ejemplo básico:
                responseDTO.setNombre(entity.getNombre());
        responseDTO.setApellido(entity.getApellido());
        responseDTO.setDireccion(entity.getDireccion());
        responseDTO.setTelefono(entity.getTelefono());
        responseDTO.setFechaRegistro(entity.getFechaRegistro());

        return responseDTO;
    }
}