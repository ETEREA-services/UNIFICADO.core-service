package unificado.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unificado.core.service.model.CuentaNegocio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CuentaNegocioRepository extends JpaRepository<CuentaNegocio, Long> {

    List<CuentaNegocio> findAllByNumeroMaestro(BigDecimal numeroMaestro);

    Optional<CuentaNegocio> findByCuentaNegocioId(Long cuentaNegocioId);

}
