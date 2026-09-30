package com.fatimalesme.tareas.repository;

import com.fatimalesme.tareas.model.Estado;
import com.fatimalesme.tareas.model.Tarea;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TEST DE REPOSITORIO: @DataJpaTest levanta SOLO la parte de JPA con H2 en memoria.
 * Comprueba que las consultas funcionan de verdad contra una base de datos.
 */
@DataJpaTest
class TareaRepositoryTest {

    @Autowired
    private TareaRepository repository;

    @Test
    void findByEstado_devuelveSoloLasTareasDeEseEstado() {
        repository.save(crearTarea("Tarea A", Estado.PENDIENTE));
        repository.save(crearTarea("Tarea B", Estado.COMPLETADA));

        List<Tarea> completadas = repository.findByEstado(Estado.COMPLETADA);

        assertEquals(1, completadas.size());
        assertEquals("Tarea B", completadas.get(0).getTitulo());
    }

    @Test
    void save_asignaIdYFechaDeCreacion() {
        Tarea guardada = repository.save(crearTarea("Tarea C", Estado.PENDIENTE));

        assertNotNull(guardada.getId());
        assertNotNull(guardada.getFechaCreacion());
    }

    private Tarea crearTarea(String titulo, Estado estado) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(titulo);
        tarea.setEstado(estado);
        return tarea;
    }
}
