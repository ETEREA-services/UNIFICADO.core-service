package unificado.core.service.negocio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unificado.core.service.negocio.model.Negocio;

import java.util.List;
import java.util.Optional;

public interface NegocioRepository extends JpaRepository<Negocio, Integer> {

    List<Negocio> findAllByOrderByNegocioId();

    Optional<Negocio> findByNegocioId(Integer negocioId);

}
