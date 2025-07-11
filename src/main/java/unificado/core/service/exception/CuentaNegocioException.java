package unificado.core.service.exception;

public class CuentaNegocioException extends RuntimeException {

    public CuentaNegocioException(Long cuentaNegocioId) {
        super("Cannot find CuentaNegocio -> " + cuentaNegocioId);
    }

}
