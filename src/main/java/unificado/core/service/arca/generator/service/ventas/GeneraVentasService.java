package unificado.core.service.arca.generator.service.ventas;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unificado.core.service.extern.negocio.core.clientemovimiento.NegocioCoreClienteMovimientoService;
import unificado.core.service.extern.negocio.core.clientemovimiento.domain.NegocioCoreClienteMovimiento;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeneraVentasService {

    private final NegocioService negocioService;
    private final NegocioCoreClienteMovimientoService negocioCoreClienteMovimientoService;

    public void generateFiles(Map<String, String> filenames, OffsetDateTime desde, OffsetDateTime hasta) throws IOException {

        var filenameVentasComprobante = filenames.get("filenameVentasComprobante");
        log.debug("Filename Ventas Comprobante: {}", filenameVentasComprobante);
        var outVentasComprobante = new BufferedWriter(new FileWriter(filenameVentasComprobante));

        var filenameVentasAlicuotas = filenames.get("filenameVentasAlicuotas");
        log.debug("Filename Ventas Alicuotas: {}", filenameVentasAlicuotas);
        var outVentasAlicuotas = new BufferedWriter(new FileWriter(filenameVentasAlicuotas));

        var filenameErroresVentas = filenames.get("filenameErroresVentas");
        log.debug("Filename Errores Ventas: {}", filenameErroresVentas);
        var outErroresVentas = new BufferedWriter(new FileWriter(filenameErroresVentas));

        var filenameTotalesVentas = filenames.get("filenameTotalesVentas");
        log.debug("Filename Totales Ventas: {}", filenameTotalesVentas);
        var outTotalesVentas = new BufferedWriter(new FileWriter(filenameTotalesVentas));

        var letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        Map<String, NegocioCoreClienteMovimiento> ventasMap = new HashMap<>();

        for (var negocio : negocioService.findAll()) {
            log.debug("Generando Ventas Negocio: {}", negocio.jsonify());

            BigDecimal neto = BigDecimal.ZERO;
            BigDecimal exento = BigDecimal.ZERO;
            BigDecimal iva21 = BigDecimal.ZERO;
            BigDecimal iva105 = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;

            for (var clienteMovimiento : Objects.requireNonNull(negocioCoreClienteMovimientoService.findAllInformacionVentas(negocio.getBackendServer(), negocio.getBackendPort(), desde, hasta).block())) {
                log.debug("Processing ClienteMovimiento -> {}", clienteMovimiento.jsonify());
                if (clienteMovimiento.getComprobante().getComprobanteAfip() == null) {
                    log.debug("Comprobante Afip no existe");
                    outErroresVentas.write("ERROR: Negocio " + negocio.getNombre() + " - Comprobante " + clienteMovimiento.getComprobante().getDescripcion() + " (" + clienteMovimiento.getComprobante().getComprobanteId() + ") SIN Tipo AFIP Asociado!!" + "\r\n");
                    continue;
                }

                var cliente = clienteMovimiento.getCliente();

                int tipoDocumento = 80;
                String numeroDocumento = cliente.getCuit().replace("-", "").trim();

                if (cliente.getPosicionIva() == 2 || (numeroDocumento.isEmpty() || numeroDocumento.equals("0"))) {
                    tipoDocumento = 96;
                    numeroDocumento = cliente.getNumeroDocumento().trim();
                }

                if (numeroDocumento.isEmpty() || numeroDocumento.equals("0")) {
                    tipoDocumento = 99;
                    numeroDocumento = "0";
                }

                if (clienteMovimiento.getImporte().abs().compareTo(BigDecimal.valueOf(1000)) > 0 && numeroDocumento.equals("0")) {
                    tipoDocumento = 96;
                    numeroDocumento = "99";
                }

                if (tipoDocumento == 80) {
                    if (!Tool.validaCuit(numeroDocumento)) {
                        outErroresVentas.write("ERROR: Negocio " + negocio.getNombre() + " - Cliente " + cliente.getRazonSocial() + " CUIT " + cliente.getCuit() + " NO Válida. Se sugiere " + "placeholder" + "\r\n");
                    }
                }

                numeroDocumento = numeroDocumento.replace("-", "").replace("Z", "2").replace("A", "4").replace("I", "1").replace(" ", "").replace("O", "0").replace("o", "0").replace(".", "").trim();

                // Remove letters
                StringBuilder stringBuilder = new StringBuilder();
                for (char c : numeroDocumento.toCharArray()) {
                    if (letras.indexOf(c) < 0) {
                        stringBuilder.append(c);
                    }
                }
                numeroDocumento = stringBuilder.toString();

                // Keep only numbers
                stringBuilder = new StringBuilder();
                for (char c : numeroDocumento.toCharArray()) {
                    if (Character.isDigit(c)) {
                        stringBuilder.append(c);
                    }
                }
                numeroDocumento = stringBuilder.toString();

                if (numeroDocumento.length() == 11 && tipoDocumento != 80) {
                    String prefix = numeroDocumento.substring(0, 2);
                    if (prefix.equals("20") || prefix.equals("23") || prefix.equals("27")) {
                        tipoDocumento = 80;
                    }
                }

                Long numeroComprobante = clienteMovimiento.getNumeroComprobante();
                BigDecimal comprobanteImporte = clienteMovimiento.getImporte();
                BigDecimal comprobanteNeto = clienteMovimiento.getNeto();
                BigDecimal comprobanteIva21 = clienteMovimiento.getMontoIva();
                BigDecimal comprobanteExento = clienteMovimiento.getMontoExento();

                if (clienteMovimiento.getMontoIvaRni().compareTo(BigDecimal.ZERO) == 0) {
                    BigDecimal calculatedNeto = comprobanteImporte.subtract(comprobanteExento).divide(BigDecimal.valueOf(1.21), 2, RoundingMode.HALF_UP);
                    if (comprobanteImporte.divide(BigDecimal.valueOf(1.21), 2, RoundingMode.HALF_UP).subtract(comprobanteNeto).abs().compareTo(BigDecimal.valueOf(0.17)) > 0) {
                        comprobanteNeto = calculatedNeto;
                        comprobanteIva21 = comprobanteImporte.subtract(comprobanteNeto);
                    }
                }

                String key = clienteMovimiento.getComprobante().getComprobanteAfip().getComprobanteAfipId() + "." +
                        clienteMovimiento.getPuntoVenta() + "." + numeroComprobante;
                if (!ventasMap.containsKey(key)) {
                    ventasMap.put(key, clienteMovimiento);
                } else {
                    outErroresVentas.write("ERROR: Negocio " + negocio.getNombre() + " - Comprobante " +
                            clienteMovimiento.getComprobante().getDescripcion() + " " +
                            clienteMovimiento.getPuntoVenta() + "/" + numeroComprobante + " REPETIDO . . .\r\n");
                    do {
                        numeroComprobante += 10000000L;
                        key = clienteMovimiento.getComprobante().getComprobanteAfip().getComprobanteAfipId() + "." +
                                clienteMovimiento.getPuntoVenta() + "." + numeroComprobante;
                    } while (ventasMap.containsKey(key));
                    ventasMap.put(key, clienteMovimiento);
                }

                // Build the line for comprobante
                StringBuilder lineaComprobante = new StringBuilder();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                lineaComprobante.append(clienteMovimiento.getFechaComprobante().format(formatter)); // Campo 1
                lineaComprobante.append(String.format("%03d", clienteMovimiento.getComprobante().getComprobanteAfip().getComprobanteAfipId())); // Campo 2
                lineaComprobante.append(String.format("%05d", clienteMovimiento.getPuntoVenta())); // Campo 3
                lineaComprobante.append(String.format("%020d", numeroComprobante)); // Campo 4
                lineaComprobante.append(String.format("%020d", numeroComprobante)); // Campo 5
                lineaComprobante.append(String.format("%02d", tipoDocumento)); // Campo 6
                lineaComprobante.append(String.format("%-20s", numeroDocumento).replace(' ', '0')); // Campo 7
                lineaComprobante.append(String.format("%-30s", Tool.replaceSymbols(cliente.getRazonSocial())), 0, 30); // Campo 8
                lineaComprobante.append(String.format("%015d", comprobanteImporte.abs().multiply(BigDecimal.valueOf(100)).longValue())); // Campo 9
                lineaComprobante.append("000000000000000"); // Campo 10
                lineaComprobante.append("000000000000000"); // Campo 11
                lineaComprobante.append(String.format("%015d", comprobanteExento.abs().multiply(BigDecimal.valueOf(100)).longValue())); // Campo 12
                lineaComprobante.append("000000000000000"); // Campo 13
                lineaComprobante.append("000000000000000"); // Campo 14
                lineaComprobante.append("000000000000000"); // Campo 15
                lineaComprobante.append("000000000000000"); // Campo 16
                String moneda = (clienteMovimiento.getMonedaId() == null || clienteMovimiento.getMonedaId() == 0 || clienteMovimiento.getMonedaId() == 1) ? "PES" : "DOL";
                lineaComprobante.append(moneda); // Campo 17
                lineaComprobante.append("0001000000"); // Campo 18
                int cantidadAlicuotas = 0;
                if (comprobanteIva21.abs().compareTo(BigDecimal.ZERO) > 0) cantidadAlicuotas++;
                if (clienteMovimiento.getMontoIvaRni().abs().compareTo(BigDecimal.ZERO) > 0) cantidadAlicuotas++;
                lineaComprobante.append(cantidadAlicuotas); // Campo 19
                lineaComprobante.append(" "); // Campo 20
                lineaComprobante.append("000000000000000"); // Campo 21
                String fechaCampo22 = clienteMovimiento.getComprobante().getComprobanteAfip().getComprobanteAfipId() == 195 ?
                        "00000000" : clienteMovimiento.getFechaComprobante().format(formatter);
                lineaComprobante.append(fechaCampo22); // Campo 22

                log.debug("Linea Comprobante -> {}",  lineaComprobante);
                outVentasComprobante.write(lineaComprobante + "\r\n");

                // Alicuota for IVA 21%
                if (comprobanteIva21.abs().compareTo(BigDecimal.ZERO) > 0) {
                    String lineaAlicuota = String.format("%03d", clienteMovimiento.getComprobante().getComprobanteAfip().getComprobanteAfipId()) + // Campo 1
                            String.format("%05d", clienteMovimiento.getPuntoVenta()) + // Campo 2
                            String.format("%020d", numeroComprobante) + // Campo 3
                            String.format("%015d", comprobanteIva21.divide(BigDecimal.valueOf(0.21), 2, RoundingMode.HALF_UP).abs().multiply(BigDecimal.valueOf(100)).longValue()) + // Campo 4
                            "0005" + // Campo 5
                            String.format("%015d", comprobanteIva21.abs().multiply(BigDecimal.valueOf(100)).longValue()); // Campo 6
                    log.debug("Linea Alicuota 21 -> {}",  lineaAlicuota);
                    outVentasAlicuotas.write(lineaAlicuota + "\r\n");
                }

                // Alicuota for IVA 10.5%
                if (clienteMovimiento.getMontoIvaRni().abs().compareTo(BigDecimal.ZERO) > 0) {
                    String lineaAlicuota = String.format("%03d", clienteMovimiento.getComprobante().getComprobanteAfip().getComprobanteAfipId()) + // Campo 1
                            String.format("%05d", clienteMovimiento.getPuntoVenta()) + // Campo 2
                            String.format("%020d", numeroComprobante) + // Campo 3
                            String.format("%015d", clienteMovimiento.getMontoIvaRni().divide(BigDecimal.valueOf(0.105), 2, RoundingMode.HALF_UP).abs().multiply(BigDecimal.valueOf(100)).longValue()) + // Campo 4
                            "0004" + // Campo 5
                            String.format("%015d", clienteMovimiento.getMontoIvaRni().abs().multiply(BigDecimal.valueOf(100)).longValue()); // Campo 6
                    log.debug("Linea Alicuota 10.5 -> {}",  lineaAlicuota);
                    outVentasAlicuotas.write(lineaAlicuota + "\r\n");
                }

                // Accumulate totals per negocio
                neto = neto.add(comprobanteNeto);
                exento = exento.add(comprobanteExento);
                iva21 = iva21.add(comprobanteIva21);
                iva105 = iva105.add(clienteMovimiento.getMontoIvaRni());
                total = total.add(comprobanteImporte);

            }
            // Write totals per negocio if total != 0
            if (total.compareTo(BigDecimal.ZERO) != 0) {
                String lineaTotales = "Negocio " + negocio.getNombre() +
                        " - Neto: " + neto.setScale(2, RoundingMode.HALF_UP) +
                        " - Exento: " + exento.setScale(2, RoundingMode.HALF_UP) +
                        " - IVA 21%: " + iva21.setScale(2, RoundingMode.HALF_UP) +
                        " - IVA 10.5%: " + iva105.setScale(2, RoundingMode.HALF_UP) +
                        " - Total: " + total.setScale(2, RoundingMode.HALF_UP);
                log.debug("Linea Totales -> {}",  lineaTotales);
                outTotalesVentas.write(lineaTotales + "\r\n");
            }

        }

        outVentasComprobante.close();
        outVentasAlicuotas.close();
        outErroresVentas.close();
        outTotalesVentas.close();
    }

}
