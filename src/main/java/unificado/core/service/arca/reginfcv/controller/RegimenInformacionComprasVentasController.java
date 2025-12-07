package unificado.core.service.arca.reginfcv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unificado.core.service.arca.reginfcv.service.RegimenInformacionComprasVentasService;
import unificado.core.service.util.Tool;

import java.io.IOException;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/unificado/core/regimen/informacion/compras/ventas")
@RequiredArgsConstructor
public class RegimenInformacionComprasVentasController {

    private final RegimenInformacionComprasVentasService service;

    @GetMapping("/generate/{desde}/{hasta}")
    public ResponseEntity<Resource> generateFiles(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime desde,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime hasta) throws IOException {
        String filename = service.generateFiles(desde, hasta, false);
        return Tool.generateFile(filename, filename);
    }

}
