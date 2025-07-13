package unificado.core.service.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import unificado.core.service.exception.NegocioException;
import unificado.core.service.model.Negocio;
import unificado.core.service.service.NegocioService;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@WebMvcTest(NegocioController.class)
class NegocioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NegocioService negocioService;

    @MockitoBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    void whenFindAll_thenReturnJsonArray() throws Exception {
        // Given
        Negocio negocio = Negocio.builder().negocioId(1).nombre("Test").build();
        given(negocioService.findAll()).willReturn(Collections.singletonList(negocio));

        // When & Then
        mockMvc.perform(get("/api/unificado/negocio/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is(negocio.getNombre())));
    }

    @Test
    void givenExistingId_whenFindByNegocioId_thenReturnNegocio() throws Exception {
        // Given
        Negocio negocio = Negocio.builder().negocioId(1).nombre("Test").build();
        given(negocioService.findByNegocioId(1)).willReturn(negocio);

        // When & Then
        mockMvc.perform(get("/api/unificado/negocio/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(negocio.getNombre())));
    }

    @Test
    void givenNonExistingId_whenFindByNegocioId_thenReturn400() throws Exception {
        // Given
        given(negocioService.findByNegocioId(99)).willThrow(new NegocioException(99));

        // When & Then
        mockMvc.perform(get("/api/unificado/negocio/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
