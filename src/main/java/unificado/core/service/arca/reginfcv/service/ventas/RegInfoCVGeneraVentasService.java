package unificado.core.service.arca.reginfcv.service.ventas;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unificado.core.service.arca.generator.service.ventas.GeneraVentasService;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegInfoCVGeneraVentasService {

    private final GeneraVentasService generaVentasService;

    public List<String> generateFiles(String path, OffsetDateTime desde, OffsetDateTime hasta) throws IOException {

        var filenameVentasComprobante = path + "REGINFO_CV_VENTAS_CBTE.txt";
        var filenameVentasAlicuotas = path + "REGINFO_CV_VENTAS_ALICUOTAS.txt";
        var filenameErroresVentas = path + "ERRORES_CV_VENTAS.txt";
        var filenameTotalesVentas = path + "TOTALES_CV_VENTAS.txt";

        Map<String, String> filenames = new HashMap<>();
        filenames.put("filenameVentasComprobante", filenameVentasComprobante);
        filenames.put("filenameVentasAlicuotas", filenameVentasAlicuotas);
        filenames.put("filenameErroresVentas", filenameErroresVentas);
        filenames.put("filenameTotalesVentas", filenameTotalesVentas);

        generaVentasService.generateFiles(filenames, desde, hasta);

        return List.of(filenameVentasComprobante, filenameVentasAlicuotas, filenameErroresVentas, filenameTotalesVentas);
    }

}
