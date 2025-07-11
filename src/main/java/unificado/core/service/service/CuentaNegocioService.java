package unificado.core.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unificado.core.service.exception.CuentaNegocioException;
import unificado.core.service.model.CuentaNegocio;
import unificado.core.service.repository.CuentaNegocioRepository;

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
