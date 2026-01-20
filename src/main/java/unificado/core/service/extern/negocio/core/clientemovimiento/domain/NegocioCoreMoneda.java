package unificado.core.service.extern.negocio.core.clientemovimiento.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NegocioCoreMoneda {

    private Integer monedaId;
    private String nombre;
    private String simbolo;
    private Integer valorId;

}
