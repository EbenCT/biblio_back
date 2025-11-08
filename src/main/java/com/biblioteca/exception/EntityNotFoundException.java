package com.biblioteca.exception;

/**
 * Excepción para entidad no encontrada
 * Generada automáticamente desde diagrama UML
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static EntityNotFoundException notFound(String entityName, Object id) {
        return new EntityNotFoundException(
            String.format("%s no encontrado con ID: %s", entityName, id)
        );
    }
}