package unificado.core.service.negocio.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(Integer negocioId) {
        super("Cannot find negocioId: " + negocioId);
    }

    public NegocioException(String message) {
        super(message);
    }

}
