package com.fatimalesme.tareas.service;

import com.fatimalesme.tareas.dto.TareaRequest;
import com.fatimalesme.tareas.dto.TareaResponse;
import com.fatimalesme.tareas.exception.TareaNotFoundException;
import com.fatimalesme.tareas.model.Estado;
import com.fatimalesme.tareas.model.Tarea;
import com.fatimalesme.tareas.repository.TareaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Aquí vive la lógica de negocio. El controlador no toca la base de datos:
 * habla con el servicio, y el servicio habla con el repositorio.
 */
@Service
public class TareaService {

    private final TareaRepository repository;

    // Inyección de dependencias por constructor (la forma recomendada)
    public TareaService(TareaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<TareaResponse> listar(Estado estado) {
        List<Tarea> tareas = (estado == null)
                ? repository.findAll()
                : repository.findByEstado(estado);
        return tareas.stream().map(this::aRespuesta).toList();
    }

    @Transactional(readOnly = true)
    public TareaResponse obtener(Long id) {
        return aRespuesta(buscarOFallar(id));
    }

    @Transactional
    public TareaResponse crear(TareaRequest request) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(request.titulo());
        tarea.setDescripcion(request.descripcion());
        tarea.setEstado(request.estado() != null ? request.estado() : Estado.PENDIENTE);
        return aRespuesta(repository.save(tarea));
    }

    @Transactional
    public TareaResponse actualizar(Long id, TareaRequest request) {
        Tarea tarea = buscarOFallar(id);
        tarea.setTitulo(request.titulo());
        tarea.setDescripcion(request.descripcion());
        if (request.estado() != null) {
            tarea.setEstado(request.estado());
        }
        return aRespuesta(repository.save(tarea));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new TareaNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private Tarea buscarOFallar(Long id) {
        return repository.findById(id).orElseThrow(() -> new TareaNotFoundException(id));
    }

    // Convierte la entidad (base de datos) en DTO (lo que ve el cliente)
    private TareaResponse aRespuesta(Tarea tarea) {
        return new TareaResponse(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getEstado(),
                tarea.getFechaCreacion()
        );
    }
}
