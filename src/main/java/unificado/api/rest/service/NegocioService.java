package unificado.api.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unificado.api.rest.exception.NegocioException;
import unificado.api.rest.kotlin.model.Negocio;
import unificado.api.rest.kotlin.repository.NegocioRepository;

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
