package unificado.core.service.model;

import org.junit.jupiter.api.Test;
import unificado.core.service.cuentamovimientounificado.model.CuentaMovimientoUnificado;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class CuentaMovimientoUnificadoTest {

    @Test
    void testCuentaMovimientoUnificadoBuilderAndFields() {
        Long id = 1L;
        Integer negocioId = 10;
        Long numeroCuenta = 12345L;
        OffsetDateTime desde = OffsetDateTime.of(2024, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime hasta = OffsetDateTime.of(2024, 1, 31, 23, 59, 59, 0, ZoneOffset.UTC);
        Byte debita = 1;
        BigDecimal numeroMaster = new BigDecimal("1000");
        BigDecimal numeroMaestro = new BigDecimal("1001.01");
        BigDecimal importe = new BigDecimal("1500.75");

        CuentaMovimientoUnificado movimiento = CuentaMovimientoUnificado.builder()
                .cuentaMovimientoUnificadoId(id)
                .negocioId(negocioId)
                .numeroCuenta(numeroCuenta)
                .desde(desde)
                .hasta(hasta)
                .debita(debita)
                .numeroMaster(numeroMaster)
                .numeroMaestro(numeroMaestro)
                .importe(importe)
                .build();

        assertEquals(id, movimiento.getCuentaMovimientoUnificadoId());
        assertEquals(negocioId, movimiento.getNegocioId());
        assertEquals(numeroCuenta, movimiento.getNumeroCuenta());
        assertEquals(desde, movimiento.getDesde());
        assertEquals(hasta, movimiento.getHasta());
        assertEquals(debita, movimiento.getDebita());
        assertEquals(numeroMaster, movimiento.getNumeroMaster());
        assertEquals(numeroMaestro, movimiento.getNumeroMaestro());
        assertEquals(importe, movimiento.getImporte());
    }

    @Test
    void testNoArgsConstructor() {
        CuentaMovimientoUnificado movimiento = new CuentaMovimientoUnificado();
        assertNull(movimiento.getCuentaMovimientoUnificadoId());
    }

    @Test
    void testAllArgsConstructor() {
        OffsetDateTime now = OffsetDateTime.now();
        CuentaMovimientoUnificado movimiento = new CuentaMovimientoUnificado(2L, 20, 54321L, now, now, (byte)0,
                new BigDecimal("2000"), new BigDecimal("2002.02"), new BigDecimal("3000.50"));

        assertEquals(2L, movimiento.getCuentaMovimientoUnificadoId());
        assertEquals(new BigDecimal("3000.50"), movimiento.getImporte());
    }

    @Test
    void testEqualsAndHashCode() {
        CuentaMovimientoUnificado cmu1 = CuentaMovimientoUnificado.builder().cuentaMovimientoUnificadoId(1L).build();
        CuentaMovimientoUnificado cmu2 = CuentaMovimientoUnificado.builder().cuentaMovimientoUnificadoId(1L).build();
        CuentaMovimientoUnificado cmu3 = CuentaMovimientoUnificado.builder().cuentaMovimientoUnificadoId(2L).build();

        assertEquals(cmu1, cmu2);
        assertNotEquals(cmu1, cmu3);
        assertEquals(cmu1.hashCode(), cmu2.hashCode());
        assertNotEquals(cmu1.hashCode(), cmu3.hashCode());
    }
}
