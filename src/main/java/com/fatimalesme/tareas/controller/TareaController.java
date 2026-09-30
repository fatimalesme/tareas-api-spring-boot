package com.fatimalesme.tareas.controller;

import com.fatimalesme.tareas.dto.TareaRequest;
import com.fatimalesme.tareas.dto.TareaResponse;
import com.fatimalesme.tareas.model.Estado;
import com.fatimalesme.tareas.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Capa web: recibe las peticiones HTTP y delega en el servicio.
 */
@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final TareaService service;

    public TareaController(TareaService service) {
        this.service = service;
    }

    // GET /api/tareas  o  GET /api/tareas?estado=PENDIENTE
    @GetMapping
    public List<TareaResponse> listar(@RequestParam(required = false) Estado estado) {
        return service.listar(estado);
    }

    // GET /api/tareas/1
    @GetMapping("/{id}")
    public TareaResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    // POST /api/tareas  -> 201 Created
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TareaResponse crear(@Valid @RequestBody TareaRequest request) {
        return service.crear(request);
    }

    // PUT /api/tareas/1
    @PutMapping("/{id}")
    public TareaResponse actualizar(@PathVariable Long id, @Valid @RequestBody TareaRequest request) {
        return service.actualizar(id, request);
    }

    // DELETE /api/tareas/1  -> 204 No Content
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
