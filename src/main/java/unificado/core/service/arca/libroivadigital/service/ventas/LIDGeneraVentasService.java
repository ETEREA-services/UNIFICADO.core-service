package unificado.core.service.arca.libroivadigital.service.ventas;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unificado.core.service.arca.generator.service.ventas.GeneraVentasService;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LIDGeneraVentasService {

    private final GeneraVentasService generaVentasService;

    public List<String> generateFiles(String path, OffsetDateTime desde, OffsetDateTime hasta) throws IOException {

        var filenameVentasComprobante = path + "LIBRO_IVA_DIGITAL_VENTAS_CBTE.txt";
        var filenameVentasAlicuotas = path + "LIBRO_IVA_DIGITAL_VENTAS_ALICUOTAS.txt";
        var filenameErroresVentas = path + "ERRORES_LID_VENTAS.txt";
        var filenameTotalesVentas = path + "TOTALES_LID_VENTAS.txt";

        Map<String, String> filenames = new HashMap<>();
        filenames.put("filenameVentasComprobante", filenameVentasComprobante);
        filenames.put("filenameVentasAlicuotas", filenameVentasAlicuotas);
        filenames.put("filenameErroresVentas", filenameErroresVentas);
        filenames.put("filenameTotalesVentas", filenameTotalesVentas);

        generaVentasService.generateFiles(filenames, desde, hasta);

        return List.of(filenameVentasComprobante, filenameVentasAlicuotas, filenameErroresVentas, filenameTotalesVentas);
    }

}
