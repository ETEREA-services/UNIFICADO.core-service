package unificado.api.rest.kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import unificado.api.rest.kotlin.model.CuentaMovimientoUnificado
import java.time.OffsetDateTime
import java.util.Optional

interface CuentaMovimientoUnificadoRepository : JpaRepository<CuentaMovimientoUnificado, Long> {

    fun findByNegocioIdAndNumeroCuentaAndDesdeAndHastaAndDebita(negocioId: Int, numeroCuenta: Long, desde: OffsetDateTime, hasta: OffsetDateTime, debita: Byte): Optional<CuentaMovimientoUnificado?>?

}