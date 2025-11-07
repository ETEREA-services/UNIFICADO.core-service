package unificado.core.service.cuentanegocio.model;

import jakarta.persistence.*;
import lombok.*;
import unificado.core.service.cuenta.model.Cuenta;
import unificado.core.service.model.Auditable;
import unificado.core.service.negocio.model.Negocio;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "cuentanegocio", uniqueConstraints = @UniqueConstraint(columnNames = {"cun_neg_id", "cun_cuenta"}))
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CuentaNegocio extends Auditable {

    @Id
    @Column(name = "clave")
    private Long cuentaNegocioId;

    @Column(name = "cun_neg_id")
    private Integer negocioId;

    @Column(name = "cun_cuenta")
    private Long numeroCuenta;

    @Column(name = "cun_maestro")
    private BigDecimal numeroMaestro;

    @Column(name = "cun_nombre")
    private String nombre;

    @OneToOne
    @JoinColumn(name = "cun_neg_id", referencedColumnName = "neg_id", insertable = false, updatable = false)
    private Negocio negocio;

    @OneToOne
    @JoinColumn(name = "cun_maestro", referencedColumnName = "pla_cuenta", insertable = false, updatable = false)
    private Cuenta cuenta;

}