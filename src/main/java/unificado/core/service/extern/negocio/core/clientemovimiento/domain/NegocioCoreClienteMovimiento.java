package unificado.core.service.extern.negocio.core.clientemovimiento.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import unificado.core.service.extern.negocio.core.common.domain.NegocioCoreComprobante;
import unificado.core.service.util.Jsonifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class NegocioCoreClienteMovimiento {

    private Long clienteMovimientoId;
    private Integer comprobanteId;
    private Integer puntoVenta = 0;
    private Long numeroComprobante = 0L;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaComprobante;
    private Long clienteId = 0L;
    private Integer legajoId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaVencimiento;
    private Integer negocioId = 0;
    private Integer empresaId = 0;
    private BigDecimal importe = BigDecimal.ZERO;
    private BigDecimal cancelado = BigDecimal.ZERO;
    private BigDecimal neto = BigDecimal.ZERO;
    private BigDecimal netoCancelado = BigDecimal.ZERO;
    private BigDecimal montoIva = BigDecimal.ZERO;
    private BigDecimal montoIvaRni = BigDecimal.ZERO;
    private BigDecimal reintegroTurista = BigDecimal.ZERO;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaContable;
    private Integer ordenContable = 0;
    private Byte recibo = 0;
    private Byte asignado = 0;
    private Byte anulada = 0;
    private Byte decreto104316 = 0;
    private String letraComprobante;
    private BigDecimal montoExento = BigDecimal.ZERO;
    private Long reservaId = 0L;
    private BigDecimal montoCuentaCorriente = BigDecimal.ZERO;
    private Long cierreCajaId = 0L;
    private Long cierreRestaurantId = 0L;
    private Integer nivel = 0;
    private Byte eliminar = 0;
    private Byte cuentaCorriente = 0;
    private String letras = "";
    private String cae = "";
    private String caeVencimiento = "";
    private String codigoBarras = "";
    private BigDecimal participacion = BigDecimal.ZERO;
    private Integer monedaId;
    private BigDecimal cotizacion = BigDecimal.ZERO;
    private String observaciones;
    private String trackUuid;
    private Long clienteMovimientoIdSlave = 0L;

    private NegocioCoreComprobante comprobante;
    private NegocioCoreCliente cliente;
    private NegocioCoreMoneda moneda;

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
