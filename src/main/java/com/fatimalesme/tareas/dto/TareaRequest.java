package com.fatimalesme.tareas.dto;

import com.fatimalesme.tareas.model.Estado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Datos que el cliente nos ENVÍA para crear o actualizar una tarea.
 * Las anotaciones definen las reglas de validación.
 */
public record TareaRequest(
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 100, message = "El título no puede superar los 100 caracteres")
        String titulo,

        @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
        String descripcion,

        Estado estado
) {
}
