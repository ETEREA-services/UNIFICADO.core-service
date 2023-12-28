package unificado.api.rest.kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import unificado.api.rest.kotlin.model.CuentaNegocio
import java.math.BigDecimal
import java.util.Optional

interface CuentaNegocioRepository : JpaRepository<CuentaNegocio, Long> {

    fun findByCuentaNegocioId(cuentaNegocioId: Long): Optional<CuentaNegocio?>?

    fun findAllByNumeroMaestro(numeroMaestro: BigDecimal): List<CuentaNegocio?>?

}