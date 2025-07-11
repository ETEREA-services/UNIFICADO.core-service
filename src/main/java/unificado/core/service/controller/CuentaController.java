package unificado.core.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unificado.core.service.model.Cuenta;
import unificado.core.service.service.CuentaService;

import java.util.List;

@RestController
@RequestMapping("/api/unificado/cuenta")
public class CuentaController {

    private final CuentaService service;

    public CuentaController(CuentaService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<Cuenta>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
}
