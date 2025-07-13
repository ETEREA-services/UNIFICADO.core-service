package unificado.core.service.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuditableTest {

    // Concrete class for testing the abstract Auditable class
    static class ConcreteAuditable extends Auditable {
    }

    @Test
    void testAuditableFields() {
        ConcreteAuditable auditable = new ConcreteAuditable();
        LocalDateTime now = LocalDateTime.now();

        auditable.setCreated(now);
        auditable.setUpdated(now);

        assertNotNull(auditable.getCreated());
        assertEquals(now, auditable.getCreated());

        assertNotNull(auditable.getUpdated());
        assertEquals(now, auditable.getUpdated());
    }
}
