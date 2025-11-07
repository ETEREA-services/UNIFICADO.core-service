package unificado.core.service.extern.negocio.core.clientemovimiento.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NegocioCorePosicionIva {

    private Integer posicionId;
    private String nombre;
    private String userId;
    private Integer idPosicionIvaArca;

}
