package com.biblioteca.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO específico para respuestas de ingreso/egreso desde app móvil
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngresoEgresoResponseDTO {
    
    private Long accesoId;
    private Long miembroId;
    private String miembroNombre;
    private String miembroApellido;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private String tipoAccion; // "INGRESO" o "EGRESO"
    private String mensaje;
    private Boolean exitoso;
    
    // Constructor para respuesta exitosa de ingreso
    public static IngresoEgresoResponseDTO ingresoExitoso(AccesoBibliotecaResponseDTO acceso) {
        IngresoEgresoResponseDTO response = new IngresoEgresoResponseDTO();
        response.setAccesoId(acceso.getId());
        response.setMiembroId(acceso.getMiembroId());
        response.setMiembroNombre(acceso.getMiembroNombre());
        response.setMiembroApellido(acceso.getMiembroApellido());
        response.setFechaEntrada(acceso.getFechaEntrada());
        response.setFechaSalida(acceso.getFechaSalida());
        response.setTipoAccion("INGRESO");
        response.setMensaje("Ingreso registrado exitosamente");
        response.setExitoso(true);
        return response;
    }
    
    // Constructor para respuesta exitosa de egreso
    public static IngresoEgresoResponseDTO egresoExitoso(AccesoBibliotecaResponseDTO acceso) {
        IngresoEgresoResponseDTO response = new IngresoEgresoResponseDTO();
        response.setAccesoId(acceso.getId());
        response.setMiembroId(acceso.getMiembroId());
        response.setMiembroNombre(acceso.getMiembroNombre());
        response.setMiembroApellido(acceso.getMiembroApellido());
        response.setFechaEntrada(acceso.getFechaEntrada());
        response.setFechaSalida(acceso.getFechaSalida());
        response.setTipoAccion("EGRESO");
        response.setMensaje("Egreso registrado exitosamente");
        response.setExitoso(true);
        return response;
    }
    
    // Constructor para respuesta de error
    public static IngresoEgresoResponseDTO error(String tipoAccion, String mensaje) {
        IngresoEgresoResponseDTO response = new IngresoEgresoResponseDTO();
        response.setTipoAccion(tipoAccion);
        response.setMensaje(mensaje);
        response.setExitoso(false);
        return response;
    }
}