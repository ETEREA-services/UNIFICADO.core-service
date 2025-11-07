package unificado.core.service.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import unificado.core.service.cuenta.model.Cuenta;
import unificado.core.service.cuenta.repository.CuentaRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CuentaRepositoryTest {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Test
    void whenSaveAndFindById_thenCorrect() {
        // Given
        BigDecimal numeroMaestro = new BigDecimal("1111.1");
        Cuenta cuenta = Cuenta.builder()
                .numeroMaestro(numeroMaestro)
                .nombre("Cuenta de Prueba")
                .integradora((byte) 1)
                .grado(1)
                .build();

        // When
        cuentaRepository.save(cuenta);
        Optional<Cuenta> foundCuenta = cuentaRepository.findById(numeroMaestro);

        // Then
        assertThat(foundCuenta).isPresent();
        assertThat(foundCuenta.get().getNombre()).isEqualTo(cuenta.getNombre());
    }
}
