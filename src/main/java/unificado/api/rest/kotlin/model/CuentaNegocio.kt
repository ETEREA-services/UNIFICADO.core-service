package unificado.api.rest.kotlin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.math.BigDecimal

@Entity
@Table(name = "cuentanegocio", uniqueConstraints = [UniqueConstraint(columnNames = ["negocioId", "numeroCuenta"])])
data class CuentaNegocio(

    @Id
    @Column(name = "clave")
    var cuentaNegocioId: Long? = null,

    @Column(name = "cun_neg_id")
    var negocioId: Int? = null,

    @Column(name = "cun_cuenta")
    var numeroCuenta: Long? = null,

    @Column(name = "cun_maestro")
    var numeroMaestro: BigDecimal? = null,

    @Column(name = "cun_nombre")
    var nombre: String = "",

    @OneToOne(optional = true)
    @JoinColumn(name = "cun_neg_id", insertable = false, updatable = false)
    var negocio: Negocio? = null,

    @OneToOne(optional = true)
    @JoinColumn(name = "cun_maestro", insertable = false, updatable = false)
    var cuenta: Cuenta? = null

) : Auditable()
