package unificado.core.service.extern.negocio.core.clientemovimiento.domain;

import lombok.Builder;
import lombok.Data;
import unificado.core.service.util.Jsonifier;
import java.math.BigDecimal;

@Data
@Builder
public class NegocioCoreCuenta {

    private Long numeroCuenta;
    private String nombre;
    private Integer negocioId;
    private Byte integra;
    private Byte transfiere;
    private Byte movimientoStock;
    private BigDecimal cuentaMaestro = BigDecimal.ZERO;
    private Integer grado;
    private Long grado1;
    private Long grado2;
    private Long grado3;
    private Long grado4;
    private Byte ventas;
    private Byte compras;
    private Byte gastos;

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
