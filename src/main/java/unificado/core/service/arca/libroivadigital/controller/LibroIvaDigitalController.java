package unificado.core.service.arca.libroivadigital.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unificado.core.service.arca.libroivadigital.service.LibroIvaDigitalService;
import unificado.core.service.util.Tool;

import java.io.IOException;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/unificado/core/libro/iva/digital")
@RequiredArgsConstructor
public class LibroIvaDigitalController {

    private final LibroIvaDigitalService service;

    @GetMapping("/generate/{desde}/{hasta}")
    public ResponseEntity<Resource> generateFiles(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime desde,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime hasta) throws IOException {
        String filename = service.generateFiles(desde, hasta);
        return Tool.generateFile(filename, filename);
    }

}
