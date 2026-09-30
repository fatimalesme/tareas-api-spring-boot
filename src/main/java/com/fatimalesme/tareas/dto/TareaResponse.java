package com.fatimalesme.tareas.dto;

import com.fatimalesme.tareas.model.Estado;
import java.time.LocalDateTime;

/**
 * Datos que nosotros DEVOLVEMOS al cliente.
 */
public record TareaResponse(
        Long id,
        String titulo,
        String descripcion,
        Estado estado,
        LocalDateTime fechaCreacion
) {
}
