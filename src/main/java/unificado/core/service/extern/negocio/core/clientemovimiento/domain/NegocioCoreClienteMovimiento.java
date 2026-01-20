package unificado.core.service.extern.negocio.core.clientemovimiento.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import unificado.core.service.extern.negocio.core.common.domain.NegocioCoreComprobante;
import unificado.core.service.util.Jsonifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NegocioCoreClienteMovimiento {

    private Long clienteMovimientoId;
    private Integer comprobanteId;
    @Builder.Default
    private Integer puntoVenta = 0;
    @Builder.Default
    private Long numeroComprobante = 0L;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaComprobante;
    @Builder.Default
    private Long clienteId = 0L;
    private Integer legajoId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaVencimiento;
    @Builder.Default
    private Integer negocioId = 0;
    @Builder.Default
    private Integer empresaId = 0;
    @Builder.Default
    private BigDecimal importe = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal cancelado = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal neto = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal netoCancelado = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal montoIva = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal montoIvaRni = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal reintegroTurista = BigDecimal.ZERO;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaContable;
    @Builder.Default
    private Integer ordenContable = 0;
    @Builder.Default
    private Byte recibo = 0;
    @Builder.Default
    private Byte asignado = 0;
    @Builder.Default
    private Byte anulada = 0;
    @Builder.Default
    private Byte decreto104316 = 0;
    private String letraComprobante;
    @Builder.Default
    private BigDecimal montoExento = BigDecimal.ZERO;
    @Builder.Default
    private Long reservaId = 0L;
    @Builder.Default
    private BigDecimal montoCuentaCorriente = BigDecimal.ZERO;
    @Builder.Default
    private Long cierreCajaId = 0L;
    @Builder.Default
    private Long cierreRestaurantId = 0L;
    @Builder.Default
    private Integer nivel = 0;
    @Builder.Default
    private Byte eliminar = 0;
    @Builder.Default
    private Byte cuentaCorriente = 0;
    @Builder.Default
    private String letras = "";
    @Builder.Default
    private String cae = "";
    @Builder.Default
    private String caeVencimiento = "";
    @Builder.Default
    private String codigoBarras = "";
    @Builder.Default
    private BigDecimal participacion = BigDecimal.ZERO;
    private Integer monedaId;
    @Builder.Default
    private BigDecimal cotizacion = BigDecimal.ZERO;
    private String observaciones;
    private String trackUuid;
    @Builder.Default
    private Long clienteMovimientoIdSlave = 0L;

    private NegocioCoreComprobante comprobante;
    private NegocioCoreCliente cliente;
    private NegocioCoreMoneda moneda;

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
