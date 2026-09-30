package com.fatimalesme.tareas;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TEST DE INTEGRACIÓN: @SpringBootTest levanta TODA la aplicación (con H2).
 * Simula el recorrido real: crear -> consultar -> borrar -> comprobar que ya no existe.
 */
@SpringBootTest
@AutoConfigureMockMvc
class TareaApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void flujoCompleto_crear_consultar_eliminar() throws Exception {
        String cuerpo = mockMvc.perform(post("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Aprender testing\",\"descripcion\":\"JUnit y Mockito\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("PENDIENTE"))
                .andReturn().getResponse().getContentAsString();

        Number id = JsonPath.read(cuerpo, "$.id");

        mockMvc.perform(get("/api/tareas/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Aprender testing"));

        mockMvc.perform(delete("/api/tareas/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/tareas/" + id))
                .andExpect(status().isNotFound());
    }
}
