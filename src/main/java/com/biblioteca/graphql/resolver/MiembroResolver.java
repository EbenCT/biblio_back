package com.biblioteca.graphql.resolver;

import com.biblioteca.domain.service.MiembroService;
import com.biblioteca.web.dto.MiembroRequestDTO;
import com.biblioteca.web.dto.MiembroResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * GraphQL Resolver para Miembro
 * Maneja todas las operaciones CRUD de miembros via GraphQL
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class MiembroResolver {

    private final MiembroService miembroService;

    // ==================== QUERIES ====================

    /**
     * Obtener todos los miembros
     */
    @QueryMapping
    public List<MiembroResponseDTO> miembros() {
        log.debug("GraphQL: Obteniendo todos los miembros");
        return miembroService.findAll();
    }

    /**
     * Obtener miembro por ID
     */
    @QueryMapping
    public MiembroResponseDTO miembro(@Argument Long id) {
        log.debug("GraphQL: Obteniendo miembro con ID: {}", id);
        return miembroService.findById(id);
    }

    // ==================== MUTATIONS ====================

    /**
     * Crear nuevo miembro
     */
    @MutationMapping
    public MiembroResponseDTO crearMiembro(@Argument MiembroRequestDTO input) {
        log.info("GraphQL: Creando nuevo miembro: {}", input);
        return miembroService.create(input);
    }

    /**
     * Actualizar miembro existente
     */
    @MutationMapping
    public MiembroResponseDTO actualizarMiembro(@Argument Long id, @Argument MiembroRequestDTO input) {
        log.info("GraphQL: Actualizando miembro con ID: {}", id);
        return miembroService.update(id, input);
    }

    /**
     * Eliminar miembro
     */
    @MutationMapping
    public Boolean eliminarMiembro(@Argument Long id) {
        log.info("GraphQL: Eliminando miembro con ID: {}", id);
        try {
            miembroService.delete(id);
            return true;
        } catch (Exception e) {
            log.error("Error al eliminar miembro: {}", e.getMessage());
            return false;
        }
    }
}