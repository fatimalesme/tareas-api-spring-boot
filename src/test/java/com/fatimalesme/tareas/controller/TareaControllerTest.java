package com.fatimalesme.tareas.controller;

import com.fatimalesme.tareas.dto.TareaRequest;
import com.fatimalesme.tareas.dto.TareaResponse;
import com.fatimalesme.tareas.exception.TareaNotFoundException;
import com.fatimalesme.tareas.model.Estado;
import com.fatimalesme.tareas.service.TareaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TEST DE CONTROLADOR: @WebMvcTest levanta SOLO la capa web.
 * El servicio es un mock; probamos códigos HTTP, JSON y validaciones.
 */
@WebMvcTest(TareaController.class)
class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TareaService service;

    @Test
    void crear_datosValidos_devuelve201() throws Exception {
        TareaResponse respuesta = new TareaResponse(1L, "Estudiar Spring", "Capitulo 1", Estado.PENDIENTE, LocalDateTime.now());
        when(service.crear(any(TareaRequest.class))).thenReturn(respuesta);

        mockMvc.perform(post("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Estudiar Spring\",\"descripcion\":\"Capitulo 1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Estudiar Spring"));
    }

    @Test
    void crear_tituloVacio_devuelve400ConErrorDeValidacion() throws Exception {
        mockMvc.perform(post("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"  \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.titulo").exists());

        verify(service, never()).crear(any());
    }

    @Test
    void obtener_idInexistente_devuelve404() throws Exception {
        when(service.obtener(99L)).thenThrow(new TareaNotFoundException(99L));

        mockMvc.perform(get("/api/tareas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void listar_estadoInvalido_devuelve400() throws Exception {
        mockMvc.perform(get("/api/tareas?estado=INVENTADO"))
                .andExpect(status().isBadRequest());
    }
}
