package unificado.core.service.service.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import unificado.core.service.exception.CuentaMovimientoUnificadoException;
import unificado.core.service.model.CuentaMovimientoUnificado;
import unificado.core.service.model.CuentaNegocio;
import unificado.core.service.service.CuentaMovimientoUnificadoService;
import unificado.core.service.service.CuentaNegocioService;
import unificado.core.service.service.extern.NegocioContableService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BalanceService {

    private final CuentaNegocioService cuentaNegocioService;
    private final NegocioContableService negocioContableService;
    private final CuentaMovimientoUnificadoService cuentaMovimientoUnificadoService;

    /**
     * Processes accounts in a reactive, non-blocking, and parallel manner.
     *
     * @return A Mono emitting a list of error strings encountered during processing.
     */
    public Mono<List<String>> processCuenta(BigDecimal numeroMaestro, OffsetDateTime desde, OffsetDateTime hasta, Boolean incluyeApertura, Boolean incluyeInflacion) {
        log.debug("Processing BalanceService.processCuenta for numeroMaestro={}", numeroMaestro);

        // Fetch all related accounts first (blocking call, happens once)
        List<CuentaNegocio> cuentasNegocio = cuentaNegocioService.findAllByNumeroMaestro(numeroMaestro);

        // Create a reactive stream from the list of accounts
        return Flux.fromIterable(cuentasNegocio)
                // Use flatMap to process each account in parallel
                .flatMap(cuentaNegocio -> processSingleCuentaNegocio(cuentaNegocio, desde, hasta, incluyeApertura, incluyeInflacion))
                // Collect all the optional error strings from the parallel executions
                .flatMap(optionalError -> optionalError.map(Mono::just).orElse(Mono.empty()))
                .collectList()
                .doOnSuccess(errors -> log.info("Finished processing for numeroMaestro={}, found {} errors.", numeroMaestro, errors.size()));
    }

    /**
     * Processes a single CuentaNegocio, including the external call and database writes,
     * returning a Mono that emits any errors found.
     */
    private Mono<Optional<String>> processSingleCuentaNegocio(CuentaNegocio cuentaNegocio, OffsetDateTime desde, OffsetDateTime hasta, Boolean incluyeApertura, Boolean incluyeInflacion) {
        logCuentaNegocio(cuentaNegocio);

        // Make the non-blocking call to the external service
        return negocioContableService.totalesEntreFechasByNegocio(
                        Objects.requireNonNull(cuentaNegocio.getNegocio()).getBackendServer(),
                        cuentaNegocio.getNegocio().getBackendPort(),
                        cuentaNegocio.getNumeroCuenta(),
                        desde,
                        hasta,
                        incluyeApertura,
                        incluyeInflacion
                )
                // After getting the totals, process them
                .flatMap(totales -> {
                    BigDecimal totalDebe = totales.getFirst();
                    BigDecimal totalHaber = totales.get(1);

                    // Handle debit and credit amounts concurrently
                    Mono<Optional<String>> debeProcessing = handleTotal(totalDebe, cuentaNegocio, desde, hasta, (byte) 1, "Deudor");
                    Mono<Optional<String>> haberProcessing = handleTotal(totalHaber, cuentaNegocio, desde, hasta, (byte) 0, "Acreedor");

                    // Combine results from both processing chains
                    return Mono.zip(debeProcessing, haberProcessing)
                            .map(tuple -> {
                                Optional<String> errorDebe = tuple.getT1();
                                Optional<String> errorHaber = tuple.getT2();
                                // For this specific logic, we only care about the first error found.
                                return errorDebe.isPresent() ? errorDebe : errorHaber;
                            });
                });
    }

    /**
     * Handles a single total (debit or credit), performing checks and database operations.
     * Blocking database calls are safely wrapped to run on a separate thread pool.
     */
    private Mono<Optional<String>> handleTotal(BigDecimal total, CuentaNegocio cuentaNegocio, OffsetDateTime desde, OffsetDateTime hasta, byte debita, String type) {
        if (total.compareTo(BigDecimal.ZERO) < 0) {
            String errorMsg = String.format("ERROR: Cuenta %s con Saldo %s NEGATIVO - %s", cuentaNegocio.getNumeroMaestro(), type, cuentaNegocio.getNegocio().getNombre());
            return Mono.just(Optional.of(errorMsg));
        }

        if (total.compareTo(BigDecimal.ZERO) > 0) {
            // Wrap the blocking database call in fromCallable and run it on the boundedElastic scheduler
            return Mono.fromCallable(() -> {
                        Long cuentaMovimientoUnificadoId = findExistingMovimientoId(cuentaNegocio, desde, hasta, debita);
                        CuentaMovimientoUnificado movimiento = new CuentaMovimientoUnificado(
                                cuentaMovimientoUnificadoId,
                                cuentaNegocio.getNegocioId(),
                                cuentaNegocio.getNumeroCuenta(),
                                desde, hasta, debita,
                                BigDecimal.ZERO,
                                cuentaNegocio.getNumeroMaestro(),
                                total
                        );
                        cuentaMovimientoUnificadoService.add(movimiento);
                        return Optional.<String>empty(); // No error
                    })
                    .subscribeOn(Schedulers.boundedElastic())
                    .doOnError(e -> log.error("Error processing {} total for cuentaNegocio {}", type, cuentaNegocio.getCuentaNegocioId(), e));
        }

        return Mono.just(Optional.empty()); // No processing needed
    }

    private Long findExistingMovimientoId(CuentaNegocio cn, OffsetDateTime desde, OffsetDateTime hasta, byte debita) {
        try {
            Long id = cuentaMovimientoUnificadoService.findByUnique(cn.getNegocioId(), cn.getNumeroCuenta(), desde, hasta, debita).getCuentaMovimientoUnificadoId();
            log.debug("Found existing movimiento with id={} for debita={}", id, debita);
            return id;
        } catch (CuentaMovimientoUnificadoException e) {
            log.debug("No existing movimiento found for debita={}, creating new.", debita);
            return null;
        }
    }

    private void logCuentaNegocio(CuentaNegocio cuentaNegocio) {
        try {
            log.debug("Processing CuentaNegocio={}", JsonMapper.builder()
                    .findAndAddModules()
                    .build()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(cuentaNegocio));
        } catch (JsonProcessingException e) {
            log.warn("Could not serialize CuentaNegocio for logging: {}", e.getMessage());
        }
    }
}
