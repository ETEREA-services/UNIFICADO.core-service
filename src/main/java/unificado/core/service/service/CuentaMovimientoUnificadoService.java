package unificado.core.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unificado.core.service.exception.CuentaMovimientoUnificadoException;
import unificado.core.service.model.CuentaMovimientoUnificado;
import unificado.core.service.repository.CuentaMovimientoUnificadoRepository;

import java.time.OffsetDateTime;

@Service
public class CuentaMovimientoUnificadoService {

    private final CuentaMovimientoUnificadoRepository repository;

    @Autowired
    public CuentaMovimientoUnificadoService(CuentaMovimientoUnificadoRepository repository) {
        this.repository = repository;
    }

    public CuentaMovimientoUnificado findByUnique(Integer negocioId, Long numeroCuenta, OffsetDateTime desde, OffsetDateTime hasta, Byte debita) {
        return repository.findByNegocioIdAndNumeroCuentaAndDesdeAndHastaAndDebita(negocioId, numeroCuenta, desde, hasta, debita).orElseThrow(() -> new CuentaMovimientoUnificadoException(negocioId, numeroCuenta, desde, hasta, debita));
    }

    public CuentaMovimientoUnificado add(CuentaMovimientoUnificado cuentaMovimientoUnificado) {
        return repository.save(cuentaMovimientoUnificado);
    }

}
