package unificado.api.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unificado.api.rest.kotlin.model.Cuenta;
import unificado.api.rest.service.CuentaService;

import java.util.List;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {

    private final CuentaService service;

    @Autowired
    public CuentaController(CuentaService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<Cuenta>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
}
