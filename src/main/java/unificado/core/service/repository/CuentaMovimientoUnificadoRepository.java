package unificado.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unificado.core.service.model.CuentaMovimientoUnificado;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface CuentaMovimientoUnificadoRepository extends JpaRepository<CuentaMovimientoUnificado, Long> {

    Optional<CuentaMovimientoUnificado> findByNegocioIdAndNumeroCuentaAndDesdeAndHastaAndDebita(Integer negocioId, Long numeroCuenta, OffsetDateTime desde, OffsetDateTime hasta, Byte debita);

}
