package unificado.core.service.cuentanegocio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unificado.core.service.cuentanegocio.model.CuentaNegocio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CuentaNegocioRepository extends JpaRepository<CuentaNegocio, Long> {

    List<CuentaNegocio> findAllByNumeroMaestro(BigDecimal numeroMaestro);

    Optional<CuentaNegocio> findByCuentaNegocioId(Long cuentaNegocioId);

}
