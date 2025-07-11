package unificado.core.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unificado.core.service.exception.NegocioException;
import unificado.core.service.model.Negocio;
import unificado.core.service.repository.NegocioRepository;

import java.util.List;

@Service
public class NegocioService {

    private final NegocioRepository repository;

    @Autowired
    public NegocioService(NegocioRepository repository) {
        this.repository = repository;
    }

    public List<Negocio> findAll() {
        return repository.findAll();
    }

    public Negocio findByNegocioId(Integer negocioId) {
        return repository.findByNegocioId(negocioId).orElseThrow(() -> new NegocioException(negocioId));
    }
}
