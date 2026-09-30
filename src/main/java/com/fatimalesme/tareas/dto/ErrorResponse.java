package com.fatimalesme.tareas.dto;

import java.util.Map;

/**
 * Formato uniforme para todos los errores de la API.
 */
public record ErrorResponse(int status, String mensaje, Map<String, String> errores) {
}
