package unificado.core.service.arca.libroivadigital.service.compras;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unificado.core.service.arca.generator.service.compras.GeneraComprasService;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LIDGeneraComprasService {

    private final GeneraComprasService generaComprasService;

    public List<String> generateFiles(String path, OffsetDateTime desde, OffsetDateTime hasta) throws IOException {

        var filenameComprasComprobante = path + "LIBRO_IVA_DIGITAL_COMPRAS_CBTE.txt";
        var filenameComprasAlicuotas = path + "LIBRO_IVA_DIGITAL_COMPRAS_ALICUOTAS.txt";
        var filenameErroresCompras = path + "ERRORES_LID_COMPRAS.txt";
        var filenameTotalesCompras = path + "TOTALES_LID_COMPRAS.txt";

        Map<String, String> filenames = new HashMap<>();
        filenames.put("filenameComprasComprobante", filenameComprasComprobante);
        filenames.put("filenameComprasAlicuotas", filenameComprasAlicuotas);
        filenames.put("filenameErroresCompras", filenameErroresCompras);
        filenames.put("filenameTotalesCompras", filenameTotalesCompras);

        generaComprasService.generateFiles(filenames, desde, hasta);

        return List.of(filenameComprasComprobante, filenameComprasAlicuotas, filenameErroresCompras, filenameTotalesCompras);
    }

}
