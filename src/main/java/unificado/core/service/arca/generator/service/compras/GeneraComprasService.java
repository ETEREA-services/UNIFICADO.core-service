package unificado.core.service.arca.generator.service.compras;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unificado.core.service.extern.negocio.core.proveedormovimiento.NegocioCoreProveedorMovimientoService;
import unificado.core.service.extern.negocio.core.proveedormovimiento.domain.NegocioCoreProveedorMovimiento;
import unificado.core.service.negocio.service.NegocioService;
import unificado.core.service.util.Tool;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeneraComprasService {

    private final NegocioService negocioService;
    private final NegocioCoreProveedorMovimientoService negocioCoreProveedorMovimientoService;

    public void generateFiles(Map<String, String> filenames, OffsetDateTime desde, OffsetDateTime hasta) throws IOException {

        var filenameComprasComprobante = filenames.get("filenameComprasComprobante");
        log.debug("Filename Compras Comprobante: {}", filenameComprasComprobante);
        var outComprasComprobante = new BufferedWriter(new FileWriter(filenameComprasComprobante));

        var filenameComprasAlicuotas = filenames.get("filenameComprasAlicuotas");
        log.debug("Filename Compras Alicuotas: {}", filenameComprasAlicuotas);
        var outComprasAlicuotas = new BufferedWriter(new FileWriter(filenameComprasAlicuotas));

        var filenameErroresCompras = filenames.get("filenameErroresCompras");
        log.debug("Filename Errores Compras: {}", filenameErroresCompras);
        var outErroresCompras = new BufferedWriter(new FileWriter(filenameErroresCompras));

        var filenameTotalesCompras = filenames.get("filenameTotalesCompras");
        log.debug("Filename Totales Compras: {}", filenameTotalesCompras);
        var outTotalesCompras = new BufferedWriter(new FileWriter(filenameTotalesCompras));

        Map<String, NegocioCoreProveedorMovimiento> comprasMap = new HashMap<>();

        for (var negocio : negocioService.findAll()) {
            log.debug("\n\nGenerando Compras Negocio: {}\n\n", negocio.jsonify());

            BigDecimal neto = BigDecimal.ZERO;
            BigDecimal exento = BigDecimal.ZERO;
            BigDecimal iva21 = BigDecimal.ZERO;
            BigDecimal iva27 = BigDecimal.ZERO;
            BigDecimal iva105 = BigDecimal.ZERO;
            BigDecimal perciva = BigDecimal.ZERO;
            BigDecimal perciibb = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;

            for (var proveedorMovimiento : Objects.requireNonNull(negocioCoreProveedorMovimientoService.findAllInformacionCompras(negocio.getBackendServer(), negocio.getBackendPort(), desde, hasta).block())) {
                if (proveedorMovimiento.getComprobante().getComprobanteAfip() == null) {
                    log.debug("\n\nComprobante Afip no existe\n\n");
                    outErroresCompras.write("ERROR: Negocio " + negocio.getNombre() + " - Comprobante " + proveedorMovimiento.getComprobante().getDescripcion() + " (" + proveedorMovimiento.getComprobante().getComprobanteId() + ") SIN Tipo AFIP Asociado!!" + "\r\n");
                    continue;
                }

                var proveedor = proveedorMovimiento.getProveedor();

                int tipoDocumento = 80;
                String numeroDocumento = proveedor.getCuit().replace("-", "").trim();

                String originalNumeroDocumento = numeroDocumento;
                if (!Tool.validaCuit(numeroDocumento)) {
                    outErroresCompras.write("ERROR: Negocio " + negocio.getNombre() + " - Proveedor " + proveedor.getRazonSocial() + " CUIT " + proveedor.getCuit() + " NO Válida. Se sugiere " + Tool.generaCuit(numeroDocumento.substring(2, 10), "M") + " - Cambiado por 20-11111111-2 . . ." + "\r\n");
                    numeroDocumento = "20111111112";
                }
                if (originalNumeroDocumento.equals("20111111112")) {
                    outErroresCompras.write("ERROR: Negocio " + negocio.getNombre() + " - Proveedor " + proveedor.getRazonSocial() + " CUIT " + proveedor.getCuit() + " NO Válida. El CUIT es de TERMALIA - Cambiado por 20-11111111-2 . . ." + "\r\n");
                    numeroDocumento = "20111111112";
                }

                if (proveedorMovimiento.getPrefijo() == 0) {
                    outErroresCompras.write("ERROR: Negocio " + negocio.getNombre() + " - Proveedor " + proveedor.getRazonSocial() + " Comprobante " + proveedorMovimiento.getComprobante().getDescripcion() + " " + proveedorMovimiento.getPrefijo() + "/" + proveedorMovimiento.getNumeroComprobante() + " con Punto de Venta 0 - Cambiado por 1 . . ." + "\r\n");
                    proveedorMovimiento.setPrefijo(1);
                }

                String key = numeroDocumento + "." + proveedorMovimiento.getPrefijo() + "." + proveedorMovimiento.getNumeroComprobante();

                if (!comprasMap.containsKey(key)) {
                    comprasMap.put(key, proveedorMovimiento);
                } else {
                    var proveedorMovimientoRepetido = comprasMap.get(key);
                    var negocioRepetido = proveedorMovimientoRepetido.getNegocio();

                    outErroresCompras.write("ERROR: Negocio " + negocio.getNombre() + " - Proveedor " + proveedor.getRazonSocial() + " Comprobante " + proveedorMovimiento.getComprobante().getDescripcion() + " " + proveedorMovimiento.getPrefijo() + "/" + proveedorMovimiento.getNumeroComprobante() + " REPETIDO en " + negocioRepetido.getNombre() + " . . ." + "\r\n");
                    do {
                        proveedorMovimiento.setNumeroComprobante(proveedorMovimiento.getNumeroComprobante() + 1);
                        key = numeroDocumento + "." + proveedorMovimiento.getPrefijo() + "." + proveedorMovimiento.getNumeroComprobante();
                    } while (comprasMap.containsKey(key));
                    comprasMap.put(key, proveedorMovimiento);
                }

                String numeroComprobante = String.valueOf(proveedorMovimiento.getNumeroComprobante()).trim();
                if (numeroComprobante.length() > 8) {
                    numeroComprobante = numeroComprobante.substring(numeroComprobante.length() - 8);
                }

                BigDecimal netoComprobante = BigDecimal.ZERO;
                BigDecimal totalComprobante = BigDecimal.ZERO;
                int cantidadAlicuotas = 0;

                if (proveedorMovimiento.getMontoIva().abs().compareTo(BigDecimal.ZERO) > 0) {
                    String lineaAlicuotas = String.format("%03d", proveedorMovimiento.getComprobante().getComprobanteAfip().getComprobanteAfipId());
                    lineaAlicuotas += String.format("%05d", proveedorMovimiento.getPrefijo());
                    lineaAlicuotas += String.format("%020d", Long.parseLong(numeroComprobante));
                    lineaAlicuotas += String.format("%02d", tipoDocumento);
                    lineaAlicuotas += String.format("%020d", Long.parseLong(numeroDocumento));
                    BigDecimal netoGravado = proveedorMovimiento.getMontoIva().abs().divide(new BigDecimal("0.21"), 2, RoundingMode.HALF_UP);
                    lineaAlicuotas += String.format("%015d", netoGravado.multiply(new BigDecimal("100")).longValue());
                    netoComprobante = netoComprobante.add(netoGravado);
                    totalComprobante = totalComprobante.add(netoGravado);
                    lineaAlicuotas += "0005";
                    lineaAlicuotas += String.format("%015d", proveedorMovimiento.getMontoIva().abs().multiply(new BigDecimal("100")).longValue());
                    outComprasAlicuotas.write(lineaAlicuotas + "\r\n");
                    cantidadAlicuotas++;
                }

                if (proveedorMovimiento.getMontoIva105().abs().compareTo(BigDecimal.ZERO) > 0) {
                    String lineaAlicuotas = String.format("%03d", proveedorMovimiento.getComprobante().getComprobanteAfip().getComprobanteAfipId());
                    lineaAlicuotas += String.format("%05d", proveedorMovimiento.getPrefijo());
                    lineaAlicuotas += String.format("%020d", Long.parseLong(numeroComprobante));
                    lineaAlicuotas += String.format("%02d", tipoDocumento);
                    lineaAlicuotas += String.format("%020d", Long.parseLong(numeroDocumento));
                    BigDecimal netoGravado = proveedorMovimiento.getMontoIva105().abs().divide(new BigDecimal("0.105"), 2, RoundingMode.HALF_UP);
                    lineaAlicuotas += String.format("%015d", netoGravado.multiply(new BigDecimal("100")).longValue());
                    netoComprobante = netoComprobante.add(netoGravado);
                    totalComprobante = totalComprobante.add(netoGravado);
                    lineaAlicuotas += "0004";
                    lineaAlicuotas += String.format("%015d", proveedorMovimiento.getMontoIva105().abs().multiply(new BigDecimal("100")).longValue());
                    outComprasAlicuotas.write(lineaAlicuotas + "\r\n");
                    cantidadAlicuotas++;
                }

                if (proveedorMovimiento.getMontoIva27().abs().compareTo(BigDecimal.ZERO) > 0) {
                    String lineaAlicuotas = String.format("%03d", proveedorMovimiento.getComprobante().getComprobanteAfip().getComprobanteAfipId());
                    lineaAlicuotas += String.format("%05d", proveedorMovimiento.getPrefijo());
                    lineaAlicuotas += String.format("%020d", Long.parseLong(numeroComprobante));
                    lineaAlicuotas += String.format("%02d", tipoDocumento);
                    lineaAlicuotas += String.format("%020d", Long.parseLong(numeroDocumento));
                    BigDecimal netoGravado = proveedorMovimiento.getMontoIva27().abs().divide(new BigDecimal("0.27"), 2, RoundingMode.HALF_UP);
                    lineaAlicuotas += String.format("%015d", netoGravado.multiply(new BigDecimal("100")).longValue());
                    netoComprobante = netoComprobante.add(netoGravado);
                    totalComprobante = totalComprobante.add(netoGravado);
                    lineaAlicuotas += "0006";
                    lineaAlicuotas += String.format("%015d", proveedorMovimiento.getMontoIva27().abs().multiply(new BigDecimal("100")).longValue());
                    outComprasAlicuotas.write(lineaAlicuotas + "\r\n");
                    cantidadAlicuotas++;
                }

                // Calcula el neto si sólo discrimina el 21%
                if (proveedorMovimiento.getMontoIva().abs().compareTo(BigDecimal.ZERO) > 0 && proveedorMovimiento.getMontoIva27().abs().compareTo(BigDecimal.ZERO) == 0 && proveedorMovimiento.getMontoIva105().abs().compareTo(BigDecimal.ZERO) == 0) {
                    BigDecimal importe = proveedorMovimiento.getImporte();
                    importe = importe.subtract(proveedorMovimiento.getGastosNoGravados());
                    importe = importe.subtract(proveedorMovimiento.getPercepcionIva());
                    importe = importe.subtract(proveedorMovimiento.getPercepcionIngresosBrutos());
                    netoComprobante = importe.divide(new BigDecimal("1.21"), 2, RoundingMode.HALF_UP);
                }

                BigDecimal importe = proveedorMovimiento.getImporte();

                totalComprobante = totalComprobante.add(proveedorMovimiento.getMontoFacturadoC().abs());
                totalComprobante = totalComprobante.add(proveedorMovimiento.getGastosNoGravados().abs());
                totalComprobante = totalComprobante.add(proveedorMovimiento.getPercepcionIva().abs());
                totalComprobante = totalComprobante.add(proveedorMovimiento.getPercepcionIngresosBrutos().abs());
                totalComprobante = totalComprobante.add(proveedorMovimiento.getMontoIva().abs());
                totalComprobante = totalComprobante.add(proveedorMovimiento.getMontoIva105().abs());
                totalComprobante = totalComprobante.add(proveedorMovimiento.getMontoIva27().abs());

                if (totalComprobante.subtract(proveedorMovimiento.getImporte().abs()).abs().compareTo(new BigDecimal("0.50")) > 0) {
                    importe = totalComprobante;
                }
                if (proveedorMovimiento.getImporte().compareTo(BigDecimal.ZERO) < 0 && importe.compareTo(BigDecimal.ZERO) > 0) {
                    importe = importe.negate();
                }

                StringBuilder lineaComprobante = new StringBuilder();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

                lineaComprobante.append(proveedorMovimiento.getFechaComprobante().format(formatter));
                lineaComprobante.append(String.format("%03d", proveedorMovimiento.getComprobante().getComprobanteAfip().getComprobanteAfipId()));
                lineaComprobante.append(String.format("%05d", proveedorMovimiento.getPrefijo()));
                lineaComprobante.append(String.format("%020d", Long.parseLong(numeroComprobante)));
                lineaComprobante.append(" ".repeat(16));
                lineaComprobante.append(String.format("%02d", tipoDocumento));
                lineaComprobante.append(String.format("%020d", Long.parseLong(numeroDocumento)));
                lineaComprobante.append(String.format("%-30s", Tool.replaceSymbols(proveedor.getRazonSocial())), 0, 30);
                lineaComprobante.append(String.format("%015d", importe.abs().multiply(new BigDecimal("100")).longValue()));
                lineaComprobante.append(String.format("%015d", proveedorMovimiento.getMontoFacturadoC().abs().add(proveedorMovimiento.getGastosNoGravados().abs()).multiply(new BigDecimal("100")).longValue()));
                lineaComprobante.append("0".repeat(15));
                lineaComprobante.append(String.format("%015d", proveedorMovimiento.getPercepcionIva().abs().multiply(new BigDecimal("100")).longValue()));
                lineaComprobante.append("0".repeat(15));
                lineaComprobante.append(String.format("%015d", proveedorMovimiento.getPercepcionIngresosBrutos().abs().multiply(new BigDecimal("100")).longValue()));
                lineaComprobante.append("0".repeat(15));
                lineaComprobante.append("0".repeat(15));
                lineaComprobante.append("PES");
                lineaComprobante.append("0001000000");
                lineaComprobante.append(cantidadAlicuotas);
                lineaComprobante.append("0");
                BigDecimal totalIva = proveedorMovimiento.getMontoIva().add(proveedorMovimiento.getMontoIva105()).add(proveedorMovimiento.getMontoIva27());
                lineaComprobante.append(String.format("%015d", totalIva.abs().multiply(new BigDecimal("100")).longValue()));
                lineaComprobante.append("0".repeat(15));
                lineaComprobante.append("0".repeat(11));
                lineaComprobante.append(" ".repeat(30));
                lineaComprobante.append("0".repeat(15));

                log.debug("Linea Comprobante -> {}", lineaComprobante);
                outComprasComprobante.write(lineaComprobante.toString() + "\r\n");

                var diferencia = netoComprobante.subtract(proveedorMovimiento.getNeto()).abs();
                if (diferencia.compareTo(new BigDecimal("0.10")) > 0) {
                    log.debug("\n\nDiferencia -> {}\n\n", diferencia);
                    log.debug("\n\nComprobante con error de neto -> {}\n\n", proveedorMovimiento.jsonify());
                    var errorDescription = "ERROR: Negocio " + negocio.getNombre() + " - Proveedor " + proveedor.getRazonSocial() + " Comprobante " + proveedorMovimiento.getComprobante().getDescripcion() + " " + proveedorMovimiento.getPrefijo() + "/" + proveedorMovimiento.getNumeroComprobante() + " con CREDITO FISCAL Inconsistente - NETO Informado: " + String.format("%.2f", proveedorMovimiento.getNeto()) + " - NETO Esperado: " + String.format("%.2f", netoComprobante) + " . . ." + "\r\n";
                    log.debug("\n\n" + errorDescription + "\n\n");
                    outErroresCompras.write(errorDescription);
                }

                diferencia = totalComprobante.subtract(proveedorMovimiento.getImporte().abs()).abs();
                if (diferencia.compareTo(new BigDecimal("0.50")) > 0) {
                    log.debug("\n\nDiferencia -> {}\n\n", diferencia);
                    log.debug("\n\nComprobante con error de total -> {}\n\n", proveedorMovimiento.jsonify());
                    var errorDescription = "ERROR: Negocio " + negocio.getNombre() + " - Proveedor " + proveedor.getRazonSocial() + " Comprobante " + proveedorMovimiento.getComprobante().getDescripcion() + " " + proveedorMovimiento.getPrefijo() + "/" + proveedorMovimiento.getNumeroComprobante() + " con TOTAL Inconsistente - TOTAL Informado: " + String.format("%.2f", proveedorMovimiento.getImporte()) + " - TOTAL Esperado: " + String.format("%.2f", totalComprobante) + " . . ." + "\r\n";
                    log.debug("\n\n" + errorDescription + "\n\n");
                    outErroresCompras.write(errorDescription);
                }

                neto = neto.add(proveedorMovimiento.getNeto());
                exento = exento.add(proveedorMovimiento.getMontoFacturadoC()).add(proveedorMovimiento.getGastosNoGravados());
                iva21 = iva21.add(proveedorMovimiento.getMontoIva());
                iva27 = iva27.add(proveedorMovimiento.getMontoIva27());
                iva105 = iva105.add(proveedorMovimiento.getMontoIva105());
                perciva = perciva.add(proveedorMovimiento.getPercepcionIva());
                perciibb = perciibb.add(proveedorMovimiento.getPercepcionIngresosBrutos());
                total = total.add(proveedorMovimiento.getImporte());
            }

            if (total.compareTo(BigDecimal.ZERO) != 0) {
                String strLinea = "Negocio " + negocio.getNombre() +
                        " - Neto: " + String.format("%.2f", neto) +
                        " - Exento: " + String.format("%.2f", exento) +
                        " - IVA 21%: " + String.format("%.2f", iva21) +
                        " - IVA 27%: " + String.format("%.2f", iva27) +
                        " - IVA 10.5%: " + String.format("%.2f", iva105) +
                        " - Perc.IVA: " + String.format("%.2f", perciva) +
                        " - Perc.IIBB: " + String.format("%.2f", perciibb) +
                        " - Total: " + String.format("%.2f", total);
                outTotalesCompras.write(strLinea + "\r\n");
            }

        }

        outComprasComprobante.close();
        outComprasAlicuotas.close();
        outErroresCompras.close();
        outTotalesCompras.close();
    }


}
