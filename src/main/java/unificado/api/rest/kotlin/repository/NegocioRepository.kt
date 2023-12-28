package unificado.api.rest.kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import unificado.api.rest.kotlin.model.Negocio
import java.util.Optional

interface NegocioRepository : JpaRepository<Negocio, Int> {

    fun findByNegocioId(negocioId: Int): Optional<Negocio?>?

}