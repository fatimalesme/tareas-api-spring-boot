package com.fatimalesme.tareas.service;

import com.fatimalesme.tareas.dto.TareaRequest;
import com.fatimalesme.tareas.dto.TareaResponse;
import com.fatimalesme.tareas.exception.TareaNotFoundException;
import com.fatimalesme.tareas.model.Estado;
import com.fatimalesme.tareas.model.Tarea;
import com.fatimalesme.tareas.repository.TareaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * TEST UNITARIO: probamos SOLO el servicio.
 * El repositorio es un "mock" (falso), así que no hay base de datos ni Spring: es rapidísimo.
 */
@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    @Mock
    private TareaRepository repository;

    @InjectMocks
    private TareaService service;

    @Test
    void crear_sinEstado_seGuardaComoPendiente() {
        when(repository.save(any(Tarea.class))).thenAnswer(inv -> inv.getArgument(0));

        TareaResponse respuesta = service.crear(new TareaRequest("Estudiar Spring", "Capitulo 1", null));

        assertEquals("Estudiar Spring", respuesta.titulo());
        assertEquals(Estado.PENDIENTE, respuesta.estado());
        verify(repository).save(any(Tarea.class));
    }

    @Test
    void obtener_idInexistente_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TareaNotFoundException.class, () -> service.obtener(99L));
    }

    @Test
    void listar_conEstado_usaElFiltroDelRepositorio() {
        Tarea tarea = new Tarea();
        tarea.setTitulo("Hacer tests");
        tarea.setEstado(Estado.COMPLETADA);
        when(repository.findByEstado(Estado.COMPLETADA)).thenReturn(List.of(tarea));

        List<TareaResponse> resultado = service.listar(Estado.COMPLETADA);

        assertEquals(1, resultado.size());
        assertEquals("Hacer tests", resultado.get(0).titulo());
        verify(repository, never()).findAll();
    }

    @Test
    void actualizar_cambiaLosDatosDeLaTarea() {
        Tarea existente = new Tarea();
        existente.setTitulo("Titulo viejo");
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Tarea.class))).thenAnswer(inv -> inv.getArgument(0));

        TareaResponse respuesta = service.actualizar(1L, new TareaRequest("Titulo nuevo", "Detalle", Estado.EN_PROGRESO));

        assertEquals("Titulo nuevo", respuesta.titulo());
        assertEquals(Estado.EN_PROGRESO, respuesta.estado());
    }

    @Test
    void eliminar_idInexistente_lanzaExcepcionYNoBorra() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(TareaNotFoundException.class, () -> service.eliminar(99L));
        verify(repository, never()).deleteById(any());
    }
}
