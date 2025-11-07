package unificado.core.service.cuenta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unificado.core.service.cuenta.model.Cuenta;

import java.math.BigDecimal;

public interface CuentaRepository extends JpaRepository<Cuenta, BigDecimal> {
}
