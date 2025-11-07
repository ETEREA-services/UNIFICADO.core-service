package unificado.core.service.extern.negocio.core.clientemovimiento.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class NegocioCoreCliente {

    private Long clienteId;
    private String nombre;
    private Integer negocioId;
    private String cuit;
    private String razonSocial;
    private String nombreFantasia;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaRestaurant;

    private Integer cantidadPaxs;
    private Integer tipoCliente;
    private String domicilio;
    private String telefono;
    private String fax;
    private String email;
    private String numeroMovil;
    private Integer posicionIva;
    private Integer constante;
    private Integer documentoId;
    private String tipoDocumento;
    private String numeroDocumento;
    private BigDecimal limiteCredito;
    private String nacionalidad;
    private Integer clienteCategoriaId;
    private String impositivoId;
    private Byte facturarExtranjero;
    private Byte bloqueado;
    private Byte discapacitado;

    private NegocioCorePosicionIva posicion;

}
