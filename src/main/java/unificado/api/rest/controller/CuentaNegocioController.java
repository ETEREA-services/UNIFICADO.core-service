package unificado.api.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unificado.api.rest.kotlin.model.CuentaNegocio;
import unificado.api.rest.service.CuentaNegocioService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cuentaNegocio")
public class CuentaNegocioController {

    private final CuentaNegocioService service;

    @Autowired
    public CuentaNegocioController(CuentaNegocioService service) {
        this.service = service;
    }

    @GetMapping("/{cuentaNegocioId}")
    public ResponseEntity<CuentaNegocio> findByCuentaNegocioId(@PathVariable Long cuentaNegocioId) {
        return new ResponseEntity<>(service.findByCuentaNegocioId(cuentaNegocioId), HttpStatus.OK);
    }

    @GetMapping("/maestro/{numeroMaestro}")
    public ResponseEntity<List<CuentaNegocio>> findAllByNumeroMaestro(@PathVariable BigDecimal numeroMaestro) {
        return new ResponseEntity<>(service.findAllByNumeroMaestro(numeroMaestro), HttpStatus.OK);
    }

}
