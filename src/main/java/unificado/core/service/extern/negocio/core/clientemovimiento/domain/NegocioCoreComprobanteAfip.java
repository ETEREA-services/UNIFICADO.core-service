package unificado.core.service.extern.negocio.core.clientemovimiento.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NegocioCoreComprobanteAfip {

    private Integer comprobanteAfipId;
    private String nombre;
    private String label;

}
