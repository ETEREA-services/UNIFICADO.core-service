package unificado.core.service.service.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import unificado.core.service.exception.CuentaMovimientoUnificadoException;
import unificado.core.service.model.CuentaMovimientoUnificado;
import unificado.core.service.model.CuentaNegocio;
import unificado.core.service.model.Negocio;
import unificado.core.service.service.CuentaMovimientoUnificadoService;
import unificado.core.service.service.CuentaNegocioService;
import unificado.core.service.service.extern.NegocioContableService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    private CuentaNegocioService cuentaNegocioService;
    @Mock
    private NegocioContableService negocioContableService;
    @Mock
    private CuentaMovimientoUnificadoService cuentaMovimientoUnificadoService;

    @InjectMocks
    private BalanceService balanceService;

    private CuentaNegocio cuentaNegocio;
    private OffsetDateTime desde;
    private OffsetDateTime hasta;

    @BeforeEach
    void setUp() {
        Negocio negocio = Negocio.builder().negocioId(1).nombre("Test Negocio").backendServer("localhost").backendPort("8080").build();
        cuentaNegocio = CuentaNegocio.builder()
                .cuentaNegocioId(1L)
                .negocioId(1)
                .numeroCuenta(123L)
                .numeroMaestro(new BigDecimal("1.01"))
                .negocio(negocio)
                .build();
        desde = OffsetDateTime.now();
        hasta = desde.plusMonths(1);
    }

    @Test
    void whenProcessCuenta_withValidTotals_returnsEmptyErrorList() {
        // Given
        when(cuentaMovimientoUnificadoService.findByUnique(any(), any(), any(), any(), any()))
                .thenThrow(new CuentaMovimientoUnificadoException(0, 0L, null, null, (byte)0));
        when(cuentaNegocioService.findAllByNumeroMaestro(any(BigDecimal.class)))
                .thenReturn(Collections.singletonList(cuentaNegocio));
        when(negocioContableService.totalesEntreFechasByNegocio(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(Mono.just(List.of(new BigDecimal("100"), new BigDecimal("50")))); // Debe, Haber
        when(cuentaMovimientoUnificadoService.add(any(CuentaMovimientoUnificado.class)))
                .thenReturn(new CuentaMovimientoUnificado());

        // When
        Mono<List<String>> result = balanceService.processCuenta(new BigDecimal("1.01"), desde, hasta, true, false);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(List::isEmpty)
                .verifyComplete();
    }

    @Test
    void whenProcessCuenta_withNegativeTotals_returnsErrorList() {
        // Given
        when(cuentaMovimientoUnificadoService.findByUnique(any(), any(), any(), any(), any()))
                .thenThrow(new CuentaMovimientoUnificadoException(0, 0L, null, null, (byte)0));
        when(cuentaNegocioService.findAllByNumeroMaestro(any(BigDecimal.class)))
                .thenReturn(Collections.singletonList(cuentaNegocio));
        when(negocioContableService.totalesEntreFechasByNegocio(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(Mono.just(List.of(new BigDecimal("-100"), new BigDecimal("50")))); // Negative Debe

        // When
        Mono<List<String>> result = balanceService.processCuenta(new BigDecimal("1.01"), desde, hasta, true, false);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(errors -> !errors.isEmpty() && errors.get(0).contains("NEGATIVO"))
                .verifyComplete();
    }
}