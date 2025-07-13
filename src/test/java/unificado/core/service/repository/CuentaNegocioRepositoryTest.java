package unificado.core.service.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import unificado.core.service.model.Cuenta;
import unificado.core.service.model.CuentaNegocio;
import unificado.core.service.model.Negocio;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CuentaNegocioRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CuentaNegocioRepository cuentaNegocioRepository;

    @Test
    void whenSaveAndFindById_thenCorrect() {
        // Given
        Negocio negocio = Negocio.builder().negocioId(1).nombre("Negocio").build();
        entityManager.persist(negocio);

        Cuenta cuenta = Cuenta.builder().numeroMaestro(new BigDecimal("1.01")).nombre("Cuenta").build();
        entityManager.persist(cuenta);

        CuentaNegocio cuentaNegocio = CuentaNegocio.builder()
                .cuentaNegocioId(1L)
                .negocioId(1)
                .numeroCuenta(12345L)
                .numeroMaestro(new BigDecimal("1.01"))
                .nombre("Cuenta Negocio Test")
                .build();
        entityManager.persist(cuentaNegocio);

        entityManager.flush();
        entityManager.clear();

        // When
        Optional<CuentaNegocio> found = cuentaNegocioRepository.findById(1L);

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getNombre()).isEqualTo(cuentaNegocio.getNombre());
        assertThat(found.get().getNegocio()).isNotNull();
        assertThat(found.get().getCuenta()).isNotNull();
        assertThat(found.get().getNegocio().getNombre()).isEqualTo("Negocio");
        assertThat(found.get().getCuenta().getNombre()).isEqualTo("Cuenta");
    }
}