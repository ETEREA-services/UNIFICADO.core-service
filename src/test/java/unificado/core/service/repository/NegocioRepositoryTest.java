package unificado.core.service.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import unificado.core.service.model.Negocio;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class NegocioRepositoryTest {

    @Autowired
    private NegocioRepository negocioRepository;

    @Test
    void whenSaveAndFindById_thenCorrect() {
        // Given
        Negocio negocio = Negocio.builder()
                .negocioId(1)
                .nombre("Test Negocio")
                .ipAddress("127.0.0.1")
                .database("testdb")
                .user("testuser")
                .build();

        // When
        negocioRepository.save(negocio);
        Negocio foundNegocio = negocioRepository.findById(1).orElse(null);

        // Then
        assertThat(foundNegocio).isNotNull();
        assertThat(foundNegocio.getNombre()).isEqualTo(negocio.getNombre());
    }
}
