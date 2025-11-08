package unificado.core.service.cuentanegocio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unificado.core.service.cuentanegocio.exception.CuentaNegocioException;
import unificado.core.service.cuentanegocio.model.CuentaNegocio;
import unificado.core.service.cuentanegocio.repository.CuentaNegocioRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaNegocioService {

    private final CuentaNegocioRepository repository;

    public CuentaNegocio findByCuentaNegocioId(Long cuentaNegocioId) {
        return repository.findByCuentaNegocioId(cuentaNegocioId).orElseThrow(() -> new CuentaNegocioException(cuentaNegocioId));
    }

    public List<CuentaNegocio> findAllByNumeroMaestro(BigDecimal numeroMaestro) {
        return repository.findAllByNumeroMaestro(numeroMaestro);
    }

}
