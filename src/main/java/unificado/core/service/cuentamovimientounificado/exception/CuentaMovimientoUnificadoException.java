package unificado.core.service.cuentamovimientounificado.exception;

import java.time.OffsetDateTime;

public class CuentaMovimientoUnificadoException extends RuntimeException {

    public CuentaMovimientoUnificadoException(Integer negocioId, Long numeroCuenta, OffsetDateTime desde, OffsetDateTime hasta, Byte debita) {
        super("Cannot find CuentaMovimientoUnificado " + negocioId + "/" + numeroCuenta + "/" + desde + "/" + hasta + "/" + debita);
    }

}
