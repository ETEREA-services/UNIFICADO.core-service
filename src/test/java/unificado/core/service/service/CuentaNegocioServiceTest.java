package unificado.core.service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unificado.core.service.exception.CuentaNegocioException;
import unificado.core.service.model.CuentaNegocio;
import unificado.core.service.repository.CuentaNegocioRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CuentaNegocioServiceTest {

    @Mock
    private CuentaNegocioRepository repository;

    @InjectMocks
    private CuentaNegocioService service;

    private CuentaNegocio cuentaNegocio;

    @BeforeEach
    void setUp() {
        cuentaNegocio = CuentaNegocio.builder()
                .cuentaNegocioId(1L)
                .nombre("Test")
                .numeroMaestro(new BigDecimal("123.45"))
                .build();
    }

    @Test
    void givenExistingId_whenFindByCuentaNegocioId_thenReturnCuentaNegocio() {
        // Given
        when(repository.findByCuentaNegocioId(1L)).thenReturn(Optional.of(cuentaNegocio));

        // When
        CuentaNegocio found = service.findByCuentaNegocioId(1L);

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getCuentaNegocioId()).isEqualTo(1L);
        verify(repository).findByCuentaNegocioId(1L);
    }

    @Test
    void givenNonExistingId_whenFindByCuentaNegocioId_thenThrowException() {
        // Given
        when(repository.findByCuentaNegocioId(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CuentaNegocioException.class, () -> {
            service.findByCuentaNegocioId(99L);
        });
        verify(repository).findByCuentaNegocioId(99L);
    }

    @Test
    void whenFindAllByNumeroMaestro_thenReturnList() {
        // Given
        BigDecimal numeroMaestro = new BigDecimal("123.45");
        when(repository.findAllByNumeroMaestro(numeroMaestro)).thenReturn(Collections.singletonList(cuentaNegocio));

        // When
        List<CuentaNegocio> result = service.findAllByNumeroMaestro(numeroMaestro);

        // Then
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getNumeroMaestro()).isEqualTo(numeroMaestro);
        verify(repository).findAllByNumeroMaestro(numeroMaestro);
    }
}
