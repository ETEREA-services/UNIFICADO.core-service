package unificado.core.service.model;

import org.junit.jupiter.api.Test;
import unificado.core.service.cuenta.model.Cuenta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testCuentaBuilderAndFields() {
        BigDecimal numeroMaestro = new BigDecimal("1001");
        BigDecimal numeroMaster = new BigDecimal("1000");
        String nombre = "Cuenta de Activo";
        Byte integradora = 1;
        Integer grado = 2;
        BigDecimal grado1 = new BigDecimal("1");
        BigDecimal grado2 = new BigDecimal("10");
        BigDecimal grado3 = new BigDecimal("100");
        BigDecimal grado4 = new BigDecimal("1000");
        Long cuentaId = 12345L;
        LocalDateTime now = LocalDateTime.now();

        Cuenta cuenta = Cuenta.builder()
                .numeroMaestro(numeroMaestro)
                .numeroMaster(numeroMaster)
                .nombre(nombre)
                .integradora(integradora)
                .grado(grado)
                .grado1(grado1)
                .grado2(grado2)
                .grado3(grado3)
                .grado4(grado4)
                .cuentaId(cuentaId)
                .build();
        cuenta.setCreated(now);
        cuenta.setUpdated(now);

        assertEquals(numeroMaestro, cuenta.getNumeroMaestro());
        assertEquals(numeroMaster, cuenta.getNumeroMaster());
        assertEquals(nombre, cuenta.getNombre());
        assertEquals(integradora, cuenta.getIntegradora());
        assertEquals(grado, cuenta.getGrado());
        assertEquals(grado1, cuenta.getGrado1());
        assertEquals(grado2, cuenta.getGrado2());
        assertEquals(grado3, cuenta.getGrado3());
        assertEquals(grado4, cuenta.getGrado4());
        assertEquals(cuentaId, cuenta.getCuentaId());
        assertEquals(now, cuenta.getCreated());
        assertEquals(now, cuenta.getUpdated());
    }

    @Test
    void testNoArgsConstructor() {
        Cuenta cuenta = new Cuenta();
        assertNull(cuenta.getCuentaId());
    }

    @Test
    void testAllArgsConstructor() {
        Cuenta cuenta = new Cuenta(new BigDecimal("2001"), new BigDecimal("2000"), "Pasivo", (byte)0, 3,
                new BigDecimal("2"), new BigDecimal("20"), new BigDecimal("200"), new BigDecimal("2000"), 54321L);
        assertEquals(new BigDecimal("2001"), cuenta.getNumeroMaestro());
        assertEquals("Pasivo", cuenta.getNombre());
    }

    @Test
    void testEqualsAndHashCode() {
        Cuenta cuenta1 = Cuenta.builder().cuentaId(1L).nombre("Test").build();
        Cuenta cuenta2 = Cuenta.builder().cuentaId(1L).nombre("Test").build();
        Cuenta cuenta3 = Cuenta.builder().cuentaId(2L).nombre("Test 2").build();

        assertEquals(cuenta1, cuenta2);
        assertNotEquals(cuenta1, cuenta3);
        assertEquals(cuenta1.hashCode(), cuenta2.hashCode());
        assertNotEquals(cuenta1.hashCode(), cuenta3.hashCode());
    }
}
