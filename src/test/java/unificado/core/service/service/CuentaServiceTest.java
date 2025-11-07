package unificado.core.service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unificado.core.service.cuenta.model.Cuenta;
import unificado.core.service.cuenta.repository.CuentaRepository;
import unificado.core.service.cuenta.service.CuentaService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CuentaServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaService cuentaService;

    @Test
    void whenFindAll_thenReturnCuentaList() {
        // Given
        Cuenta cuenta = Cuenta.builder().nombre("Test").build();
        when(cuentaRepository.findAll()).thenReturn(Collections.singletonList(cuenta));

        // When
        List<Cuenta> cuentas = cuentaService.findAll();

        // Then
        assertThat(cuentas).isNotNull().hasSize(1);
        verify(cuentaRepository).findAll();
    }
}
