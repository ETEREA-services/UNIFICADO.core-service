package unificado.api.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import unificado.api.rest.exception.NegocioException;
import unificado.api.rest.kotlin.model.Negocio;
import unificado.api.rest.service.NegocioService;

import java.util.List;

@RestController
@RequestMapping("/negocio")
public class NegocioController {

    private final NegocioService service;

    @Autowired
    public NegocioController(NegocioService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<Negocio>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{negocioId}")
    public ResponseEntity<Negocio> findByNegocioId(@PathVariable Integer negocioId) {
        try {
            return new ResponseEntity<>(service.findByNegocioId(negocioId), HttpStatus.OK);
        } catch (NegocioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
