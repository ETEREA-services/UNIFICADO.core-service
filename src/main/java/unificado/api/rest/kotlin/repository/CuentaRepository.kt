package unificado.api.rest.kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import unificado.api.rest.kotlin.model.Cuenta
import java.math.BigDecimal

interface CuentaRepository : JpaRepository<Cuenta, BigDecimal> {
}