package unificado.api.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unificado.api.rest.exception.CuentaMovimientoUnificadoException;
import unificado.api.rest.kotlin.model.CuentaMovimientoUnificado;
import unificado.api.rest.kotlin.repository.CuentaMovimientoUnificadoRepository;

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
