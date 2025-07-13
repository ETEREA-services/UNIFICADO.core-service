package unificado.core.service.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import unificado.core.service.model.CuentaMovimientoUnificado;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CuentaMovimientoUnificadoRepositoryTest {

    @Autowired
    private CuentaMovimientoUnificadoRepository repository;

    @Test
    void whenSaveAndFindById_thenCorrect() {
        // Given
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        CuentaMovimientoUnificado movimiento = CuentaMovimientoUnificado.builder()
                .negocioId(1)
                .numeroCuenta(12345L)
                .desde(now)
                .hasta(now.plusHours(1))
                .debita((byte) 1)
                .importe(new BigDecimal("100.50"))
                .build();

        // When
        movimiento = repository.save(movimiento);
        Optional<CuentaMovimientoUnificado> found = repository.findById(movimiento.getCuentaMovimientoUnificadoId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getImporte()).isEqualByComparingTo(new BigDecimal("100.50"));
        assertThat(found.get().getNegocioId()).isEqualTo(1);
    }
}
