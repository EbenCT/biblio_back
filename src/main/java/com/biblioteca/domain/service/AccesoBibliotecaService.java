package com.biblioteca.domain.service;

import com.biblioteca.domain.model.AccesoBiblioteca;
import com.biblioteca.domain.model.Miembro;
import com.biblioteca.domain.repository.AccesoBibliotecaRepository;
import com.biblioteca.domain.repository.MiembroRepository;
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
import java.util.Optional;
import java.util.UUID;
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
    private final MiembroRepository miembroRepository;

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

    // ==================== MÉTODOS ESPECÍFICOS PARA INGRESO/EGRESO ====================

    /**
     * Generar código QR único para ingreso/egreso
     * Este código será usado por la web para generar el QR visual
     */
    public String generarCodigoQR() {
        String codigo = "BIBLIO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        log.info("Código QR generado: {}", codigo);
        return codigo;
    }

    /**
     * Registrar INGRESO de un miembro escaneando QR
     * Llamado desde la app móvil cuando escanea QR
     * 
     * Permite múltiples accesos por día - el miembro puede entrar y salir varias veces,
     * pero debe registrar salida antes de volver a entrar.
     */
    public AccesoBibliotecaResponseDTO registrarIngreso(Long miembroId, String codigoQr) {
        log.info("Registrando ingreso - MiembroId: {}, CodigoQR: {}", miembroId, codigoQr);

        // Verificar que el miembro existe
        Miembro miembro = miembroRepository.findById(miembroId)
            .orElseThrow(() -> EntityNotFoundException.notFound("Miembro", miembroId));

        // Verificar que no tenga un acceso activo (que no haya salido)
        // Permite múltiples accesos al día, pero debe registrar salida antes de volver a entrar
        Optional<AccesoBiblioteca> accesoActivo = accesoBibliotecaRepository.findActivoByMiembro(miembroId);
        if (accesoActivo.isPresent()) {
            throw new IllegalStateException("El miembro ya está dentro de la biblioteca. Debe registrar salida primero.");
        }

        // Crear nuevo registro de acceso
        AccesoBiblioteca nuevoAcceso = new AccesoBiblioteca();
        nuevoAcceso.setMiembro(miembro);
        nuevoAcceso.setFechaEntrada(LocalDateTime.now());
        nuevoAcceso.setFechaSalida(null); // No ha salido aún
        nuevoAcceso.setCodigoQr(codigoQr);
        nuevoAcceso.setMetodoAcceso("QR_MOVIL");
        nuevoAcceso.setEstado(true); // Activo = está dentro

        AccesoBiblioteca savedAcceso = accesoBibliotecaRepository.save(nuevoAcceso);
        
        log.info("Ingreso registrado exitosamente - ID: {}", savedAcceso.getId());
        return mapEntityToResponse(savedAcceso);
    }

    /**
     * Registrar EGRESO de un miembro escaneando QR  
     * Llamado desde la app móvil cuando escanea QR para salir
     */
    public AccesoBibliotecaResponseDTO registrarEgreso(Long miembroId, String codigoQr) {
        log.info("Registrando egreso - MiembroId: {}, CodigoQR: {}", miembroId, codigoQr);

        // Buscar el acceso activo del miembro
        AccesoBiblioteca accesoActivo = accesoBibliotecaRepository.findActivoByMiembro(miembroId)
            .orElseThrow(() -> new IllegalStateException("No se encontró un acceso activo para este miembro"));

        // Actualizar con fecha de salida
        accesoActivo.setFechaSalida(LocalDateTime.now());
        accesoActivo.setEstado(false); // Ya no está dentro
        
        AccesoBiblioteca updatedAcceso = accesoBibliotecaRepository.save(accesoActivo);
        
        log.info("Egreso registrado exitosamente - ID: {}", updatedAcceso.getId());
        return mapEntityToResponse(updatedAcceso);
    }

    /**
     * Verificar si un miembro está actualmente dentro de la biblioteca
     */
    @Transactional(readOnly = true)
    public boolean estaAdentro(Long miembroId) {
        return accesoBibliotecaRepository.findActivoByMiembro(miembroId).isPresent();
    }

    /**
     * Obtener todos los miembros que están actualmente dentro de la biblioteca
     */
    @Transactional(readOnly = true)
    public List<AccesoBibliotecaResponseDTO> obtenerMiembrosAdentro() {
        log.debug("Obteniendo miembros actualmente dentro de la biblioteca");
        
        return accesoBibliotecaRepository.findMiembrosActivos()
            .stream()
            .map(this::mapEntityToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Obtener historial de accesos de un miembro específico
     * Para la funcionalidad futura de la app móvil
     */
    @Transactional(readOnly = true)
    public List<AccesoBibliotecaResponseDTO> obtenerHistorialMiembro(Long miembroId) {
        log.debug("Obteniendo historial de accesos para miembro ID: {}", miembroId);
        
        return accesoBibliotecaRepository.findByMiembroIdOrderByFechaEntradaDesc(miembroId)
            .stream()
            .map(this::mapEntityToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Obtener reporte de accesos por rango de fechas
     * Para el panel administrativo web
     */
    @Transactional(readOnly = true)
    public List<AccesoBibliotecaResponseDTO> obtenerReportePorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        log.debug("Obteniendo reporte de accesos desde {} hasta {}", fechaInicio, fechaFin);
        
        return accesoBibliotecaRepository.findByFechaEntradaBetweenOrderByFechaEntradaDesc(fechaInicio, fechaFin)
            .stream()
            .map(this::mapEntityToResponse)
            .collect(Collectors.toList());
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

        // Mapeo de campos específicos
        responseDTO.setFechaEntrada(entity.getFechaEntrada());
        responseDTO.setFechaSalida(entity.getFechaSalida());
        responseDTO.setMetodoAcceso(entity.getMetodoAcceso());
        responseDTO.setCodigoQr(entity.getCodigoQr());
        responseDTO.setEstado(entity.getEstado());

        // Mapeo de información del miembro si existe
        if (entity.getMiembro() != null) {
            responseDTO.setMiembroId(entity.getMiembro().getId());
            responseDTO.setMiembroNombre(entity.getMiembro().getNombre());
            responseDTO.setMiembroApellido(entity.getMiembro().getApellido());
        }

        return responseDTO;
    }
}