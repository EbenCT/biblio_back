package com.biblioteca.graphql.resolver;

import com.biblioteca.domain.service.LibroService;
import com.biblioteca.web.dto.LibroRequestDTO;
import com.biblioteca.web.dto.LibroResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Resolver GraphQL para Libro
 * Reutiliza el LibroService existente sin modificar la lógica REST
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class LibroResolver {

    private final LibroService libroService;

    // === QUERIES ===
    
    @QueryMapping
    public List<LibroResponseDTO> libros() {
        log.info("GraphQL Query: Obteniendo todos los libros");
        return libroService.findAll();
    }

    @QueryMapping
    public LibroResponseDTO libro(@Argument Long id) {
        log.info("GraphQL Query: Obteniendo libro con ID: {}", id);
        return libroService.findById(id);
    }

    @QueryMapping
    public List<LibroResponseDTO> librosPorCategoria(@Argument String categoria) {
        log.info("GraphQL Query: Obteniendo libros por categoría: {}", categoria);
        return libroService.findAll().stream()
                .filter(libro -> libro.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    @QueryMapping
    public List<LibroResponseDTO> librosDisponibles() {
        log.info("GraphQL Query: Obteniendo libros disponibles");
        return libroService.findAll().stream()
                .filter(LibroResponseDTO::getDisponible)
                .collect(Collectors.toList());
    }

    // === MUTATIONS ===

    @MutationMapping
    public LibroResponseDTO crearLibro(@Argument LibroInput input) {
        log.info("GraphQL Mutation: Creando libro: {}", input);
        
        // Convertimos el input GraphQL al DTO REST existente
        LibroRequestDTO requestDTO = new LibroRequestDTO(
                input.getTitulo(),
                input.getAutor(),
                input.getCategoria(),
                input.getDisponible()
        );
        
        return libroService.create(requestDTO);
    }

    @MutationMapping
    public LibroResponseDTO actualizarLibro(@Argument Long id, @Argument LibroInput input) {
        log.info("GraphQL Mutation: Actualizando libro con ID: {}", id);
        
        LibroRequestDTO requestDTO = new LibroRequestDTO(
                input.getTitulo(),
                input.getAutor(),
                input.getCategoria(),
                input.getDisponible()
        );
        
        return libroService.update(id, requestDTO);
    }

    @MutationMapping
    public Boolean eliminarLibro(@Argument Long id) {
        log.info("GraphQL Mutation: Eliminando libro con ID: {}", id);
        try {
            libroService.delete(id);
            return true;
        } catch (Exception e) {
            log.error("Error eliminando libro: {}", e.getMessage());
            return false;
        }
    }

    // Clase interna para el input GraphQL
    public static class LibroInput {
        private String titulo;
        private String autor;
        private String categoria;
        private Boolean disponible;

        // Getters y setters
        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }
        
        public String getAutor() { return autor; }
        public void setAutor(String autor) { this.autor = autor; }
        
        public String getCategoria() { return categoria; }
        public void setCategoria(String categoria) { this.categoria = categoria; }
        
        public Boolean getDisponible() { return disponible; }
        public void setDisponible(Boolean disponible) { this.disponible = disponible; }

        @Override
        public String toString() {
            return String.format("LibroInput{titulo='%s', autor='%s', categoria='%s', disponible=%s}", 
                    titulo, autor, categoria, disponible);
        }
    }
}