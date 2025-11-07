package unificado.core.service.exception;

import org.junit.jupiter.api.Test;
import unificado.core.service.cuentanegocio.exception.CuentaNegocioException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CuentaNegocioExceptionTest {

    @Test
    void testConstructorWithLong() {
        Long cuentaNegocioId = 456L;
        CuentaNegocioException exception = assertThrows(CuentaNegocioException.class, () -> {
            throw new CuentaNegocioException(cuentaNegocioId);
        });
        assertEquals("Cannot find CuentaNegocio -> " + cuentaNegocioId, exception.getMessage());
    }
}
