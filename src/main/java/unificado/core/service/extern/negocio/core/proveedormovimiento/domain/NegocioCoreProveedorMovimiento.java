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
    @Builder.Default
    private Integer prefijo = 0;
    @Builder.Default
    private Long numeroComprobante = 0L;
    @Builder.Default
    private BigDecimal importe = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal cancelado = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal aplicado = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal neto = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal netoCancelado = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal montoIva = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal montoIva27 = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal montoIva105 = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal percepcionIva = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal percepcionIngresosBrutos = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal gastosNoGravados = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal ajustes = BigDecimal.ZERO;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaContable;
    private Integer ordenContable;
    @Builder.Default
    private BigDecimal montoFacturadoC = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal montoSujetoRetencionesIIBB = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal montoRetencionesIIBB = BigDecimal.ZERO;
    private Long codigoRetencionesIIBB;
    private Long numeroComprobanteRetencionesIIBB;
    @Builder.Default
    private String concepto = "";
    private Long cierreCajaId;
    private Integer nivel;
    private Integer negocioIdPaga;
    @Builder.Default
    private Byte concursada = 0;
    @Builder.Default
    private BigDecimal importeConcursado = BigDecimal.ZERO;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaContableConcurso;
    private Integer ordenContableConcurso;
    @Builder.Default
    private Byte marca = 0;
    @Builder.Default
    private Integer orden = 0;
    @Builder.Default
    private Byte transferida = 0;
    private NegocioCoreComprobante comprobante;
    private NegocioCoreProveedor proveedor;
    private NegocioCoreNegocio negocio;

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
