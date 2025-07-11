package unificado.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unificado.core.service.model.Cuenta;

import java.math.BigDecimal;

public interface CuentaRepository extends JpaRepository<Cuenta, BigDecimal> {
}
