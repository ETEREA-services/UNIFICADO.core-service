package unificado.core.service.exception;

import org.junit.jupiter.api.Test;
import unificado.core.service.negocio.exception.NegocioException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NegocioExceptionTest {

    @Test
    void testConstructorWithInteger() {
        Integer negocioId = 123;
        NegocioException exception = assertThrows(NegocioException.class, () -> {
            throw new NegocioException(negocioId);
        });
        assertEquals("Cannot find negocioId: " + negocioId, exception.getMessage());
    }

    @Test
    void testConstructorWithString() {
        String errorMessage = "Test error message";
        NegocioException exception = assertThrows(NegocioException.class, () -> {
            throw new NegocioException(errorMessage);
        });
        assertEquals(errorMessage, exception.getMessage());
    }
}
