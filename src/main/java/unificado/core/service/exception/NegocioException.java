package unificado.core.service.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(Integer negocioId) {
        super("Cannot find negocioId: " + negocioId);
    }

    public NegocioException(String message) {
        super(message);
    }

}
