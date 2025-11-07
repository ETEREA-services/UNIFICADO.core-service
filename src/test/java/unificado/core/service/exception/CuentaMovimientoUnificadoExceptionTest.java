package unificado.core.service.exception;

import org.junit.jupiter.api.Test;
import unificado.core.service.cuentamovimientounificado.exception.CuentaMovimientoUnificadoException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CuentaMovimientoUnificadoExceptionTest {

    @Test
    void testConstructorWithMultipleArguments() {
        Integer negocioId = 1;
        Long numeroCuenta = 12345L;
        OffsetDateTime desde = OffsetDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime hasta = OffsetDateTime.of(2023, 1, 31, 23, 59, 59, 0, ZoneOffset.UTC);
        Byte debita = 1;

        CuentaMovimientoUnificadoException exception = assertThrows(CuentaMovimientoUnificadoException.class, () -> {
            throw new CuentaMovimientoUnificadoException(negocioId, numeroCuenta, desde, hasta, debita);
        });

        String expectedMessage = "Cannot find CuentaMovimientoUnificado " + negocioId + "/" + numeroCuenta + "/" + desde + "/" + hasta + "/" + debita;
        assertEquals(expectedMessage, exception.getMessage());
    }
}
