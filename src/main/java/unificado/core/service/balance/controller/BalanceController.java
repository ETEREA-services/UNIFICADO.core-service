package unificado.core.service.balance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unificado.core.service.balance.service.BalanceService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/unificado/core/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService service;

    @GetMapping("/generate/processCuenta/{numeroMaestro}/{desde}/{hasta}/{incluyeApertura}/{incluyeInflacion}")
    public ResponseEntity<List<String>> processCuenta(@PathVariable BigDecimal numeroMaestro, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime desde,
                                                      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime hasta, @PathVariable Boolean incluyeApertura, @PathVariable Boolean incluyeInflacion) {
        List<String> errors = service.processCuenta(numeroMaestro, desde, hasta, incluyeApertura, incluyeInflacion).block();
        return new ResponseEntity<>(errors, HttpStatus.OK);
    }

}
