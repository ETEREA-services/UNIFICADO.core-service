package unificado.core.service.negocio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import unificado.core.service.negocio.exception.NegocioException;
import unificado.core.service.negocio.model.Negocio;
import unificado.core.service.negocio.service.NegocioService;

import java.util.List;

@RestController
@RequestMapping("/api/unificado/core/negocio")
@RequiredArgsConstructor
public class NegocioController {

    private final NegocioService service;

    @GetMapping("/")
    public ResponseEntity<List<Negocio>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{negocioId}")
    public ResponseEntity<Negocio> findByNegocioId(@PathVariable Integer negocioId) {
        try {
            return ResponseEntity.ok(service.findByNegocioId(negocioId));
        } catch (NegocioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
