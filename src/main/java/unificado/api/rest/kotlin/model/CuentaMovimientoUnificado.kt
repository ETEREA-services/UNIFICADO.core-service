package unificado.api.rest.kotlin.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(
    name = "movconunif",
    uniqueConstraints = [UniqueConstraint(columnNames = ["negocioId", "numeroCuenta", "desde", "hasta", "debita"])]
)
data class CuentaMovimientoUnificado(

    @Id
    @Column(name = "clave")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cuentaMovimientoUnificadoId: Long? = null,

    @Column(name = "mco_neg_id")
    var negocioId: Int? = null,

    @Column(name = "mco_cuenta")
    var numeroCuenta: Long? = null,

    @Column(name = "mco_desde")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var desde: OffsetDateTime? = null,

    @Column(name = "mco_hasta")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var hasta: OffsetDateTime? = null,

    @Column(name = "mco_debita")
    var debita: Byte = 0,

    @Column(name = "mco_master")
    var numeroMaster: BigDecimal = BigDecimal.ZERO,

    @Column(name = "mco_maestro")
    var numeroMaestro: BigDecimal? = null,

    @Column(name = "mco_importe")
    var importe: BigDecimal = BigDecimal.ZERO

) : Auditable()
