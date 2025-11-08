package unificado.core.service.cuentanegocio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unificado.core.service.cuentanegocio.model.CuentaNegocio;
import unificado.core.service.cuentanegocio.service.CuentaNegocioService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/unificado/core/cuentaNegocio")
@RequiredArgsConstructor
public class CuentaNegocioController {

    private final CuentaNegocioService service;

    @GetMapping("/{cuentaNegocioId}")
    public ResponseEntity<CuentaNegocio> findByCuentaNegocioId(@PathVariable Long cuentaNegocioId) {
        return new ResponseEntity<>(service.findByCuentaNegocioId(cuentaNegocioId), HttpStatus.OK);
    }

    @GetMapping("/maestro/{numeroMaestro}")
    public ResponseEntity<List<CuentaNegocio>> findAllByNumeroMaestro(@PathVariable BigDecimal numeroMaestro) {
        return new ResponseEntity<>(service.findAllByNumeroMaestro(numeroMaestro), HttpStatus.OK);
    }

}
