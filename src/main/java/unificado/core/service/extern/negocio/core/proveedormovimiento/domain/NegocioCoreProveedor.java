package unificado.core.service.extern.negocio.core.proveedormovimiento.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NegocioCoreProveedor {

    private Long proveedorId;
    private String razonSocial;
    private Integer negocioId;
    private String cuit;
    private String domicilio;
    private String telefono;
    private String fax;
    private String email;
    private Integer posicion;
    private String celular;
    private String numeroInscripcionIngresosBrutos;
    private Integer ingresosBrutosContribuyenteId;
    private Integer proveedorCategoriaId;
    private Integer ingresosBrutosCategoriaId;
    private Integer reparticionId;
    private Byte transporte;

}
