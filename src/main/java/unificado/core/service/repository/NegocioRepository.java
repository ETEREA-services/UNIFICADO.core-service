package unificado.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unificado.core.service.model.Negocio;

import java.util.Optional;

public interface NegocioRepository extends JpaRepository<Negocio, Integer> {

    Optional<Negocio> findByNegocioId(Integer negocioId);
}
