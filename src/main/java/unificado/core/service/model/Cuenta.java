package unificado.core.service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "plan")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cuenta extends Auditable {

    @Id
    @Column(name = "pla_cuenta")
    private BigDecimal numeroMaestro;

    @Column(name = "pla_cuentamaster")
    private BigDecimal numeroMaster;

    @Column(name = "pla_nombre")
    private String nombre;

    @Column(name = "pla_integra")
    private Byte integradora;

    @Column(name = "pla_grado")
    private Integer grado;

    @Column(name = "pla_grado1")
    private BigDecimal grado1;

    @Column(name = "pla_grado2")
    private BigDecimal grado2;

    @Column(name = "pla_grado3")
    private BigDecimal grado3;

    @Column(name = "pla_grado4")
    private BigDecimal grado4;

    @Column(name = "clave")
    private Long cuentaId;

}