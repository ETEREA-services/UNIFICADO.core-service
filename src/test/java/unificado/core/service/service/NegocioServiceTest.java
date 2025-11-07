package unificado.core.service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unificado.core.service.negocio.exception.NegocioException;
import unificado.core.service.negocio.model.Negocio;
import unificado.core.service.negocio.repository.NegocioRepository;
import unificado.core.service.negocio.service.NegocioService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NegocioServiceTest {

    @Mock
    private NegocioRepository negocioRepository;

    @InjectMocks
    private NegocioService negocioService;

    private Negocio negocio;

    @BeforeEach
    void setUp() {
        negocio = Negocio.builder()
                .negocioId(1)
                .nombre("Test Negocio")
                .build();
    }

    @Test
    void whenFindAll_thenReturnNegocioList() {
        // Given
        when(negocioRepository.findAllByOrderByNegocioId()).thenReturn(Collections.singletonList(negocio));

        // When
        List<Negocio> negocios = negocioService.findAll();

        // Then
        assertThat(negocios).isNotNull().hasSize(1);
        assertThat(negocios.get(0).getNombre()).isEqualTo("Test Negocio");
        verify(negocioRepository).findAllByOrderByNegocioId();
    }

    @Test
    void givenExistingId_whenFindByNegocioId_thenReturnNegocio() {
        // Given
        when(negocioRepository.findByNegocioId(1)).thenReturn(Optional.of(negocio));

        // When
        Negocio found = negocioService.findByNegocioId(1);

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getNegocioId()).isEqualTo(1);
        verify(negocioRepository).findByNegocioId(1);
    }

    @Test
    void givenNonExistingId_whenFindByNegocioId_thenThrowNegocioException() {
        // Given
        when(negocioRepository.findByNegocioId(99)).thenReturn(Optional.empty());

        // When & Then
        NegocioException exception = assertThrows(NegocioException.class, () -> {
            negocioService.findByNegocioId(99);
        });
        
        assertThat(exception.getMessage()).contains("99");
        verify(negocioRepository).findByNegocioId(99);
    }
}
