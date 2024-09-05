package unificado.api.rest.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "plan")
data class Cuenta(

    @Id
    @Column(name = "pla_cuenta")
    var numeroMaestro: BigDecimal? = null,

    @Column(name = "pla_cuentamaster")
    var numeroMaster: BigDecimal? = null,

    @Column(name = "pla_nombre")
    var nombre: String = "",

    @Column(name = "pla_integra")
    var integradora: Byte = 0,

    @Column(name = "pla_grado")
    var grado: Int = 0,

    @Column(name = "pla_grado1")
    var grado1: BigDecimal = BigDecimal.ZERO,

    @Column(name = "pla_grado2")
    var grado2: BigDecimal = BigDecimal.ZERO,

    @Column(name = "pla_grado3")
    var grado3: BigDecimal = BigDecimal.ZERO,

    @Column(name = "pla_grado4")
    var grado4: BigDecimal = BigDecimal.ZERO,

    @Column(name = "clave")
    var cuentaId: Long? = null

) : Auditable()
