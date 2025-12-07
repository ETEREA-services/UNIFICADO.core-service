package unificado.core.service.arca.libroivadigital.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import unificado.core.service.arca.libroivadigital.service.compras.LIDGeneraComprasService;
import unificado.core.service.arca.libroivadigital.service.ventas.LIDGeneraVentasService;
import unificado.core.service.util.Tool;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class LibroIvaDigitalService {

    private final Environment environment;
    private final LIDGeneraVentasService lIDGeneraVentasService;
    private final LIDGeneraComprasService lIDGeneraComprasService;

    public String generateFiles(OffsetDateTime desde, OffsetDateTime hasta, Boolean correccionCompras) throws IOException {

        var path = environment.getProperty("path.files");
        var outputFilename = path + "LibroIvaDigital.zip";

        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(outputFilename));

        log.debug("Generando ventas de negocios");
        var filenames = lIDGeneraVentasService.generateFiles(path, desde, hasta);
        for (var filename : filenames) {
            Tool.addFileToZip(zipOutputStream, filename);
        }

        log.debug("Generando compras de negocios");
        filenames = lIDGeneraComprasService.generateFiles(path, desde, hasta, correccionCompras);
        for (var filename : filenames) {
            Tool.addFileToZip(zipOutputStream, filename);
        }

        zipOutputStream.close();
        return outputFilename;
    }

}
