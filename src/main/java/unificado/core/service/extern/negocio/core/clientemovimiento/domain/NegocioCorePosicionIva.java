package unificado.core.service.extern.negocio.core.clientemovimiento.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NegocioCorePosicionIva {

    private Integer posicionId;
    private String nombre;
    private String userId;
    private Integer idPosicionIvaArca;

}
