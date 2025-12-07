package unificado.core.service.extern.negocio.core.proveedormovimiento.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorMovimientoNetoAjusteRequestDto {

    private Long proveedorMovimientoId;
    private BigDecimal netoAjustado;
    private BigDecimal montoIva21Ajustado;
    private BigDecimal montoIva105Ajustado;
    private BigDecimal montoIva27Ajustado;

}
