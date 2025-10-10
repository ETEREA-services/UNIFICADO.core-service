package unificado.core.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unificado.core.service.negocio.exception.NegocioException;
import unificado.core.service.negocio.model.Negocio;
import unificado.core.service.negocio.repository.NegocioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NegocioService {

    private final NegocioRepository repository;

    public List<Negocio> findAll() {
        return repository.findAll();
    }

    public Negocio findByNegocioId(Integer negocioId) {
        return repository.findByNegocioId(negocioId).orElseThrow(() -> new NegocioException(negocioId));
    }
}
