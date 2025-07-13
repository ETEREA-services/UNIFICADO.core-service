package unificado.core.service.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NegocioTest {

    @Test
    void testNegocioBuilderAndFields() {
        Integer negocioId = 1;
        String nombre = "Test Negocio";
        String ipAddress = "127.0.0.1";
        String database = "testdb";
        String user = "testuser";
        Byte backup = 1;
        String backendServer = "localhost";
        String backendPort = "8080";
        LocalDateTime now = LocalDateTime.now();

        Negocio negocio = Negocio.builder()
                .negocioId(negocioId)
                .nombre(nombre)
                .ipAddress(ipAddress)
                .database(database)
                .user(user)
                .backup(backup)
                .backendServer(backendServer)
                .backendPort(backendPort)
                .build();
        negocio.setCreated(now);
        negocio.setUpdated(now);


        assertEquals(negocioId, negocio.getNegocioId());
        assertEquals(nombre, negocio.getNombre());
        assertEquals(ipAddress, negocio.getIpAddress());
        assertEquals(database, negocio.getDatabase());
        assertEquals(user, negocio.getUser());
        assertEquals(backup, negocio.getBackup());
        assertEquals(backendServer, negocio.getBackendServer());
        assertEquals(backendPort, negocio.getBackendPort());
        assertEquals(now, negocio.getCreated());
        assertEquals(now, negocio.getUpdated());
    }

    @Test
    void testNoArgsConstructor() {
        Negocio negocio = new Negocio();
        assertNull(negocio.getNegocioId());
    }

    @Test
    void testAllArgsConstructor() {
        Negocio negocio = new Negocio(2, "AllArgs", "192.168.1.1", "db", "user", (byte)0, "server", "9090");
        assertEquals(2, negocio.getNegocioId());
        assertEquals("AllArgs", negocio.getNombre());
    }

    @Test
    void testEqualsAndHashCode() {
        Negocio negocio1 = Negocio.builder().negocioId(1).nombre("Test").build();
        Negocio negocio2 = Negocio.builder().negocioId(1).nombre("Test").build();
        Negocio negocio3 = Negocio.builder().negocioId(2).nombre("Test 2").build();

        assertEquals(negocio1, negocio2);
        assertNotEquals(negocio1, negocio3);
        assertEquals(negocio1.hashCode(), negocio2.hashCode());
        assertNotEquals(negocio1.hashCode(), negocio3.hashCode());
    }
}
