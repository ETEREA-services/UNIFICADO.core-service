package unificado.core.service.arca.reginfcv.service.compras;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unificado.core.service.arca.generator.service.compras.GeneraComprasService;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegInfoCVGeneraComprasService {

    private final GeneraComprasService generaComprasService;

    public List<String> generateFiles(String path, OffsetDateTime desde, OffsetDateTime hasta, Boolean correccionCompras) throws IOException {
        
        var filenameComprasComprobante = path + "REGINFO_CV_COMPRAS_CBTE.txt";
        var filenameComprasAlicuotas = path + "REGINFO_CV_COMPRAS_ALICUOTAS.txt";
        var filenameErroresCompras = path + "ERRORES_CV_COMPRAS.txt";
        var filenameTotalesCompras = path + "TOTALES_CV_COMPRAS.txt";

        Map<String, String> filenames = new HashMap<>();
        filenames.put("filenameComprasComprobante", filenameComprasComprobante);
        filenames.put("filenameComprasAlicuotas", filenameComprasAlicuotas);
        filenames.put("filenameErroresCompras", filenameErroresCompras);
        filenames.put("filenameTotalesCompras", filenameTotalesCompras);

        generaComprasService.generateFiles(filenames, desde, hasta, correccionCompras);

        return List.of(filenameComprasComprobante, filenameComprasAlicuotas, filenameErroresCompras, filenameTotalesCompras);
    }

}
