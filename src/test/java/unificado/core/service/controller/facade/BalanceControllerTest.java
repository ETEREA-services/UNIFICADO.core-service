package unificado.core.service.controller.facade;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;
import unificado.core.service.service.facade.BalanceService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(BalanceController.class)
class BalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BalanceService balanceService;

    @MockitoBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    void whenProcessCuenta_thenReturnListOfErrors() throws Exception {
        // Given
        BigDecimal numeroMaestro = new BigDecimal("1.01");
        OffsetDateTime desde = OffsetDateTime.now();
        OffsetDateTime hasta = desde.plusMonths(1);
        String desdeStr = desde.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        String hastaStr = hasta.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        given(balanceService.processCuenta(any(), any(), any(), any(), any()))
                .willReturn(Mono.just(Collections.emptyList()));

        // When & Then
        mockMvc.perform(get("/api/unificado/balance/generate/processCuenta/{numeroMaestro}/{desde}/{hasta}/{incluyeApertura}/{incluyeInflacion}",
                numeroMaestro, desdeStr, hastaStr, true, false)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
