package unificado.core.service.model;

import org.junit.jupiter.api.Test;
import unificado.core.service.cuenta.model.Cuenta;
import unificado.core.service.cuentanegocio.model.CuentaNegocio;
import unificado.core.service.negocio.model.Negocio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CuentaNegocioTest {

    @Test
    void testCuentaNegocioBuilderAndFields() {
        Long cuentaNegocioId = 1L;
        Integer negocioId = 10;
        Long numeroCuenta = 123456789L;
        BigDecimal numeroMaestro = new BigDecimal("1101.01");
        String nombre = "Cuenta Específica de Negocio";
        LocalDateTime now = LocalDateTime.now();
        Negocio mockNegocio = mock(Negocio.class);
        Cuenta mockCuenta = mock(Cuenta.class);

        CuentaNegocio cuentaNegocio = CuentaNegocio.builder()
                .cuentaNegocioId(cuentaNegocioId)
                .negocioId(negocioId)
                .numeroCuenta(numeroCuenta)
                .numeroMaestro(numeroMaestro)
                .nombre(nombre)
                .negocio(mockNegocio)
                .cuenta(mockCuenta)
                .build();
        cuentaNegocio.setCreated(now);
        cuentaNegocio.setUpdated(now);

        assertEquals(cuentaNegocioId, cuentaNegocio.getCuentaNegocioId());
        assertEquals(negocioId, cuentaNegocio.getNegocioId());
        assertEquals(numeroCuenta, cuentaNegocio.getNumeroCuenta());
        assertEquals(numeroMaestro, cuentaNegocio.getNumeroMaestro());
        assertEquals(nombre, cuentaNegocio.getNombre());
        assertEquals(mockNegocio, cuentaNegocio.getNegocio());
        assertEquals(mockCuenta, cuentaNegocio.getCuenta());
        assertEquals(now, cuentaNegocio.getCreated());
        assertEquals(now, cuentaNegocio.getUpdated());
    }

    @Test
    void testNoArgsConstructor() {
        CuentaNegocio cuentaNegocio = new CuentaNegocio();
        assertNull(cuentaNegocio.getCuentaNegocioId());
    }

    @Test
    void testAllArgsConstructor() {
        Negocio mockNegocio = mock(Negocio.class);
        Cuenta mockCuenta = mock(Cuenta.class);
        CuentaNegocio cuentaNegocio = new CuentaNegocio(2L, 20, 987654321L, new BigDecimal("2202.02"), "AllArgs CN", mockNegocio, mockCuenta);

        assertEquals(2L, cuentaNegocio.getCuentaNegocioId());
        assertEquals("AllArgs CN", cuentaNegocio.getNombre());
        assertEquals(mockNegocio, cuentaNegocio.getNegocio());
        assertEquals(mockCuenta, cuentaNegocio.getCuenta());
    }

    @Test
    void testEqualsAndHashCode() {
        CuentaNegocio cn1 = CuentaNegocio.builder().cuentaNegocioId(1L).nombre("Test").build();
        CuentaNegocio cn2 = CuentaNegocio.builder().cuentaNegocioId(1L).nombre("Test").build();
        CuentaNegocio cn3 = CuentaNegocio.builder().cuentaNegocioId(2L).nombre("Test 2").build();

        assertEquals(cn1, cn2);
        assertNotEquals(cn1, cn3);
        assertEquals(cn1.hashCode(), cn2.hashCode());
        assertNotEquals(cn1.hashCode(), cn3.hashCode());
    }
}
