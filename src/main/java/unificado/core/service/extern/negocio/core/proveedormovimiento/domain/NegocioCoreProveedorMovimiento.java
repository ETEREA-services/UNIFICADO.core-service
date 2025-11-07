package unificado.core.service.extern.negocio.core.proveedormovimiento.domain;

import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import unificado.core.service.extern.negocio.core.common.domain.NegocioCoreComprobante;
import unificado.core.service.extern.negocio.core.common.domain.NegocioCoreNegocio;
import unificado.core.service.util.Jsonifier;

@Data
@Builder
public class NegocioCoreProveedorMovimiento {

    private Long proveedorMovimientoId;
    private Integer empresaId;
    private Integer negocioId;
    private Long proveedorId;
    private Integer comprobanteId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaComprobante;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaVencimiento;
    private Integer prefijo = 0;
    private Long numeroComprobante = 0L;
    private BigDecimal importe = BigDecimal.ZERO;
    private BigDecimal cancelado = BigDecimal.ZERO;
    private BigDecimal aplicado = BigDecimal.ZERO;
    private BigDecimal neto = BigDecimal.ZERO;
    private BigDecimal netoCancelado = BigDecimal.ZERO;
    private BigDecimal montoIva = BigDecimal.ZERO;
    private BigDecimal montoIva27 = BigDecimal.ZERO;
    private BigDecimal montoIva105 = BigDecimal.ZERO;
    private BigDecimal percepcionIva = BigDecimal.ZERO;
    private BigDecimal percepcionIngresosBrutos = BigDecimal.ZERO;
    private BigDecimal gastosNoGravados = BigDecimal.ZERO;
    private BigDecimal ajustes = BigDecimal.ZERO;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaContable;
    private Integer ordenContable;
    private BigDecimal montoFacturadoC = BigDecimal.ZERO;
    private BigDecimal montoSujetoRetencionesIIBB = BigDecimal.ZERO;
    private BigDecimal montoRetencionesIIBB = BigDecimal.ZERO;
    private Long codigoRetencionesIIBB;
    private Long numeroComprobanteRetencionesIIBB;
    private String concepto = "";
    private Long cierreCajaId;
    private Integer nivel;
    private Integer negocioIdPaga;
    private Byte concursada = 0;
    private BigDecimal importeConcursado = BigDecimal.ZERO;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaContableConcurso;
    private Integer ordenContableConcurso;
    private Byte marca = 0;
    private Integer orden = 0;
    private Byte transferida = 0;
    private NegocioCoreComprobante comprobante;
    private NegocioCoreProveedor proveedor;
    private NegocioCoreNegocio negocio;

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
