package unificado.api.rest.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(Integer negocioId) {
        super("Cannot find negocioId: " + negocioId);
    }

}
