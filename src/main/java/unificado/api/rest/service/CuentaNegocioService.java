package unificado.api.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unificado.api.rest.exception.CuentaNegocioException;
import unificado.api.rest.kotlin.model.CuentaNegocio;
import unificado.api.rest.kotlin.repository.CuentaNegocioRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaNegocioService {

    private final CuentaNegocioRepository repository;

    @Autowired
    public CuentaNegocioService(CuentaNegocioRepository repository) {
        this.repository = repository;
    }

    public CuentaNegocio findByCuentaNegocioId(Long cuentaNegocioId) {
        return repository.findByCuentaNegocioId(cuentaNegocioId).orElseThrow(() -> new CuentaNegocioException(cuentaNegocioId));
    }

    public List<CuentaNegocio> findAllByNumeroMaestro(BigDecimal numeroMaestro) {
        return repository.findAllByNumeroMaestro(numeroMaestro);
    }

}
