package unificado.core.service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Negocio extends Auditable {

    @Id
    @Column(name = "neg_id")
    private Integer negocioId;

    @Column(name = "neg_nombre")
    private String nombre;

    @Column(name = "neg_ip")
    private String ipAddress;

    @Column(name = "neg_db")
    private String database;

    @Column(name = "neg_user")
    private String user;

    private Byte backup;
    private String backendServer;
    private String backendPort;

}