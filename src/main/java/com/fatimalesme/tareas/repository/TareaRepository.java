package com.fatimalesme.tareas.repository;

import com.fatimalesme.tareas.model.Estado;
import com.fatimalesme.tareas.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Spring Data JPA genera la implementación sola.
 * Ya trae save, findById, findAll, deleteById, existsById...
 * Además, findByEstado se convierte en SQL a partir del nombre del método.
 */
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findByEstado(Estado estado);
}
