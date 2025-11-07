package unificado.core.service.extern.negocio.core.clientemovimiento.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NegocioCoreMoneda {

    private Integer monedaId;
    private String nombre;
    private String simbolo;
    private Integer valorId;

}
