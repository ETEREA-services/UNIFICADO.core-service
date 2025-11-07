package unificado.core.service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unificado.core.service.cuentamovimientounificado.exception.CuentaMovimientoUnificadoException;
import unificado.core.service.cuentamovimientounificado.model.CuentaMovimientoUnificado;
import unificado.core.service.cuentamovimientounificado.repository.CuentaMovimientoUnificadoRepository;
import unificado.core.service.cuentamovimientounificado.service.CuentaMovimientoUnificadoService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CuentaMovimientoUnificadoServiceTest {

    @Mock
    private CuentaMovimientoUnificadoRepository repository;

    @InjectMocks
    private CuentaMovimientoUnificadoService service;

    private CuentaMovimientoUnificado movimiento;
    private Integer negocioId = 1;
    private Long numeroCuenta = 12345L;
    private OffsetDateTime desde = OffsetDateTime.now(ZoneOffset.UTC);
    private OffsetDateTime hasta = desde.plusHours(1);
    private Byte debita = 1;

    @BeforeEach
    void setUp() {
        movimiento = CuentaMovimientoUnificado.builder()
                .negocioId(negocioId)
                .numeroCuenta(numeroCuenta)
                .desde(desde)
                .hasta(hasta)
                .debita(debita)
                .build();
    }

    @Test
    void whenAdd_thenSaveAndReturn() {
        // Given
        when(repository.save(any(CuentaMovimientoUnificado.class))).thenReturn(movimiento);

        // When
        CuentaMovimientoUnificado saved = service.add(movimiento);

        // Then
        assertThat(saved).isNotNull();
        verify(repository).save(movimiento);
    }

    @Test
    void givenExistingUnique_whenFindByUnique_thenReturnMovimiento() {
        // Given
        when(repository.findByNegocioIdAndNumeroCuentaAndDesdeAndHastaAndDebita(negocioId, numeroCuenta, desde, hasta, debita))
                .thenReturn(Optional.of(movimiento));

        // When
        CuentaMovimientoUnificado found = service.findByUnique(negocioId, numeroCuenta, desde, hasta, debita);

        // Then
        assertThat(found).isNotNull();
        verify(repository).findByNegocioIdAndNumeroCuentaAndDesdeAndHastaAndDebita(negocioId, numeroCuenta, desde, hasta, debita);
    }

    @Test
    void givenNonExistingUnique_whenFindByUnique_thenThrowException() {
        // Given
        when(repository.findByNegocioIdAndNumeroCuentaAndDesdeAndHastaAndDebita(any(), any(), any(), any(), any()))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(CuentaMovimientoUnificadoException.class, () -> {
            service.findByUnique(negocioId, numeroCuenta, desde, hasta, debita);
        });
    }
}
