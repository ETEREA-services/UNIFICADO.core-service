package unificado.api.rest.kotlin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table
data class Negocio(

    @Id
    @Column(name = "neg_id")
    var negocioId: Int? = null,

    @Column(name = "neg_nombre")
    var nombre: String = "",

    @Column(name = "neg_ip")
    var ipAddress: String? = null,

    @Column(name = "neg_db")
    var database: String? = null,

    @Column(name = "neg_user")
    var user: String? = null,

    var backup: Byte = 0,
    var backendServer: String = "",
    var backendPort: String = ""

) : Auditable()
