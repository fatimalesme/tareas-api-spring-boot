package com.fatimalesme.tareas.exception;

public class TareaNotFoundException extends RuntimeException {

    public TareaNotFoundException(Long id) {
        super("No existe la tarea con id " + id);
    }
}
