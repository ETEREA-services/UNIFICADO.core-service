package unificado.core.service.arca.reginfcv.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import unificado.core.service.arca.reginfcv.service.cabecera.RegInfoCVGeneraCabeceraService;
import unificado.core.service.arca.reginfcv.service.compras.RegInfoCVGeneraComprasService;
import unificado.core.service.arca.reginfcv.service.ventas.RegInfoCVGeneraVentasService;
import unificado.core.service.util.Tool;

import java.io.*;
import java.time.OffsetDateTime;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegimenInformacionComprasVentasService {

    private final Environment environment;
    private final RegInfoCVGeneraCabeceraService regInfoCVGeneraCabeceraService;
    private final RegInfoCVGeneraVentasService regInfoCVGeneraVentasService;
    private final RegInfoCVGeneraComprasService regInfoCVGeneraComprasService;

    @Value("${app.cuit}")
    private String cuit;

    public String generateFiles(OffsetDateTime desde, OffsetDateTime hasta) throws IOException {
        var path = environment.getProperty("path.files");
        var outputFilename = path + "RegInfCV.zip";

        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(outputFilename));

        log.debug("Generating Regimen Informacion Compras Ventas - Generando Cabecera");
        var filenameCabecera = regInfoCVGeneraCabeceraService.generateFile(cuit, desde, path);

        log.debug("Agregando cabecera al zip");
        Tool.addFileToZip(zipOutputStream, filenameCabecera);

        log.debug("Generando ventas de negocios");
        var filenames = regInfoCVGeneraVentasService.generateFiles(path, desde, hasta);
        for (var filename : filenames) {
            Tool.addFileToZip(zipOutputStream, filename);
        }

        log.debug("Generando compras de negocios");
        filenames = regInfoCVGeneraComprasService.generateFiles(path, desde, hasta);
        for (var filename : filenames) {
            Tool.addFileToZip(zipOutputStream, filename);
        }

        zipOutputStream.close();
        return outputFilename;
    }

}
