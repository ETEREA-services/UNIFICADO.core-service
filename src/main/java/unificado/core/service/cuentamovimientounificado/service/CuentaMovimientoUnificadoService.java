package unificado.core.service.cuentamovimientounificado.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unificado.core.service.cuentamovimientounificado.exception.CuentaMovimientoUnificadoException;
import unificado.core.service.cuentamovimientounificado.model.CuentaMovimientoUnificado;
import unificado.core.service.cuentamovimientounificado.repository.CuentaMovimientoUnificadoRepository;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class CuentaMovimientoUnificadoService {

    private final CuentaMovimientoUnificadoRepository repository;

    public CuentaMovimientoUnificado findByUnique(Integer negocioId, Long numeroCuenta, OffsetDateTime desde, OffsetDateTime hasta, Byte debita) {
        return repository.findByNegocioIdAndNumeroCuentaAndDesdeAndHastaAndDebita(negocioId, numeroCuenta, desde, hasta, debita).orElseThrow(() -> new CuentaMovimientoUnificadoException(negocioId, numeroCuenta, desde, hasta, debita));
    }

    public CuentaMovimientoUnificado add(CuentaMovimientoUnificado cuentaMovimientoUnificado) {
        return repository.save(cuentaMovimientoUnificado);
    }

}
