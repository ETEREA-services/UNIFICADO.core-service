package unificado.core.service.cuentamovimientounificado.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import unificado.core.service.model.Auditable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(
    name = "movconunif",
    uniqueConstraints = @UniqueConstraint(columnNames = {"mco_neg_id", "mco_cuenta", "mco_desde", "mco_hasta", "mco_debita"})
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CuentaMovimientoUnificado extends Auditable {

    @Id
    @Column(name = "clave")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuentaMovimientoUnificadoId;

    @Column(name = "mco_neg_id")
    private Integer negocioId;

    @Column(name = "mco_cuenta")
    private Long numeroCuenta;

    @Column(name = "mco_desde")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime desde;

    @Column(name = "mco_hasta")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime hasta;

    @Column(name = "mco_debita")
    private Byte debita;

    @Column(name = "mco_master")
    private BigDecimal numeroMaster;

    @Column(name = "mco_maestro")
    private BigDecimal numeroMaestro;

    @Column(name = "mco_importe")
    private BigDecimal importe;

}