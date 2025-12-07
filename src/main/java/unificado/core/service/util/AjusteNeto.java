package unificado.core.service.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class AjusteNeto {

    private BigDecimal neto;
    private BigDecimal montoIva21;
    private BigDecimal montoIva105;
    private BigDecimal montoIva27;

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
