package unificado.api.rest.service.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unificado.api.rest.exception.CuentaMovimientoUnificadoException;
import unificado.api.rest.kotlin.model.CuentaMovimientoUnificado;
import unificado.api.rest.kotlin.model.CuentaNegocio;
import unificado.api.rest.service.CuentaMovimientoUnificadoService;
import unificado.api.rest.service.CuentaNegocioService;
import unificado.api.rest.service.extern.NegocioContableService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BalanceService {

    private final CuentaNegocioService cuentaNegocioService;

    private final NegocioContableService negocioContableService;

    private final CuentaMovimientoUnificadoService cuentaMovimientoUnificadoService;

    @Autowired
    public BalanceService(CuentaNegocioService cuentaNegocioService, NegocioContableService negocioContableService, CuentaMovimientoUnificadoService cuentaMovimientoUnificadoService) {
        this.cuentaNegocioService = cuentaNegocioService;
        this.negocioContableService = negocioContableService;
        this.cuentaMovimientoUnificadoService = cuentaMovimientoUnificadoService;
    }

    public List<String> processCuenta(BigDecimal numeroMaestro, OffsetDateTime desde, OffsetDateTime hasta, Boolean incluyeApertura, Boolean incluyeInflacion) {
        List<String> errors = new ArrayList<>();
        for (CuentaNegocio cuentaNegocio : cuentaNegocioService.findAllByNumeroMaestro(numeroMaestro)) {
            log.debug("cuentaNegocio={}", cuentaNegocio);
            List<BigDecimal> totales = negocioContableService.totalesEntreFechasByNegocio(cuentaNegocio.getNegocio().getBackendServer(), cuentaNegocio.getNegocio().getBackendPort(), cuentaNegocio.getNumeroCuenta(), desde, hasta, incluyeApertura, incluyeInflacion);
            BigDecimal totalDebe = totales.get(0);
            if (totalDebe.compareTo(BigDecimal.ZERO) < 0) {
                errors.add("ERROR: Cuenta " + cuentaNegocio.getNumeroMaestro() + " con Saldo Deudor NEGATIVO - " + cuentaNegocio.getNegocio().getNombre());
            }
            if (totalDebe.compareTo(BigDecimal.ZERO) > 0) {
                Long cuentaMovimientoUnificadoId = null;
                try {
                    cuentaMovimientoUnificadoId = cuentaMovimientoUnificadoService.findByUnique(cuentaNegocio.getNegocioId(), cuentaNegocio.getNumeroCuenta(), desde, hasta, (byte) 1).getCuentaMovimientoUnificadoId();
                    log.debug("debe cuentaMovimientoUnificadoId={}", cuentaMovimientoUnificadoId);
                } catch (CuentaMovimientoUnificadoException e) {
                    log.debug("new debe row");
                }
                cuentaMovimientoUnificadoService.add(new CuentaMovimientoUnificado(cuentaMovimientoUnificadoId, cuentaNegocio.getNegocioId(), cuentaNegocio.getNumeroCuenta(), desde, hasta, (byte) 1, BigDecimal.ZERO, cuentaNegocio.getNumeroMaestro(), totalDebe));
            }
            BigDecimal totalHaber = totales.get(1);
            if (totalHaber.compareTo(BigDecimal.ZERO) < 0) {
                errors.add("ERROR: Cuenta " + cuentaNegocio.getNumeroMaestro() + " con Saldo Acreedor NEGATIVO - " + cuentaNegocio.getNegocio().getNombre());
            }
            if (totalHaber.compareTo(BigDecimal.ZERO) > 0) {
                Long cuentaMovimientoUnificadoId = null;
                try {
                    cuentaMovimientoUnificadoId = cuentaMovimientoUnificadoService.findByUnique(cuentaNegocio.getNegocioId(), cuentaNegocio.getNumeroCuenta(), desde, hasta, (byte) 0).getCuentaMovimientoUnificadoId();
                    log.debug("haber cuentaMovimientoUnificadoId={}", cuentaMovimientoUnificadoId);
                } catch (CuentaMovimientoUnificadoException e) {
                    log.debug("new haber row");
                }
                cuentaMovimientoUnificadoService.add(new CuentaMovimientoUnificado(cuentaMovimientoUnificadoId, cuentaNegocio.getNegocioId(), cuentaNegocio.getNumeroCuenta(), desde, hasta, (byte) 0, BigDecimal.ZERO, cuentaNegocio.getNumeroMaestro(), totalHaber));
            }
        }
        return errors;
    }
}
