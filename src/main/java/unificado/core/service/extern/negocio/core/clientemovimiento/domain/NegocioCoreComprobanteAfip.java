package unificado.core.service.extern.negocio.core.clientemovimiento.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NegocioCoreComprobanteAfip {

    private Integer comprobanteAfipId;
    private String nombre;
    private String label;

}
