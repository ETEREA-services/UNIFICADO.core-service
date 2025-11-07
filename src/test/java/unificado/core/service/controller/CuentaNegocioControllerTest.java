package unificado.core.service.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import unificado.core.service.cuentanegocio.controller.CuentaNegocioController;
import unificado.core.service.cuentanegocio.model.CuentaNegocio;
import unificado.core.service.cuentanegocio.service.CuentaNegocioService;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@WebMvcTest(CuentaNegocioController.class)
class CuentaNegocioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CuentaNegocioService service;

    @MockitoBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    void givenExistingId_whenFindByCuentaNegocioId_thenReturnCuentaNegocio() throws Exception {
        // Given
        CuentaNegocio cuentaNegocio = CuentaNegocio.builder().cuentaNegocioId(1L).nombre("Test").build();
        given(service.findByCuentaNegocioId(1L)).willReturn(cuentaNegocio);

        // When & Then
        mockMvc.perform(get("/api/unificado/cuentaNegocio/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(cuentaNegocio.getNombre())));
    }

    @Test
    void whenFindAllByNumeroMaestro_thenReturnJsonArray() throws Exception {
        // Given
        BigDecimal numeroMaestro = new BigDecimal("123.45");
        CuentaNegocio cuentaNegocio = CuentaNegocio.builder().numeroMaestro(numeroMaestro).nombre("Test").build();
        given(service.findAllByNumeroMaestro(numeroMaestro)).willReturn(Collections.singletonList(cuentaNegocio));

        // When & Then
        mockMvc.perform(get("/api/unificado/cuentaNegocio/maestro/123.45")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is(cuentaNegocio.getNombre())));
    }
}
