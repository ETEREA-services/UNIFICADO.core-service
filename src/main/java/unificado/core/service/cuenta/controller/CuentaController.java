package unificado.core.service.cuenta.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unificado.core.service.cuenta.model.Cuenta;
import unificado.core.service.cuenta.service.CuentaService;

import java.util.List;

@RestController
@RequestMapping("/api/unificado/cuenta")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService service;

    @GetMapping("/")
    public ResponseEntity<List<Cuenta>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

}
