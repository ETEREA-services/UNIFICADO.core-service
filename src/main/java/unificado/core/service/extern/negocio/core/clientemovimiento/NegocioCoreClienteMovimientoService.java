package unificado.core.service.extern.negocio.core.clientemovimiento;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import unificado.core.service.extern.negocio.core.clientemovimiento.domain.NegocioCoreClienteMovimiento;
import unificado.core.service.negocio.exception.NegocioException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NegocioCoreClienteMovimientoService {

    private final WebClient.Builder webClientBuilder;

    private String getUrl(String server, String port) {
        return "http://" + server + ":" + port;
    }

    public Mono<List<NegocioCoreClienteMovimiento>> findAllInformacionVentas(String backendServer, String backendPort, OffsetDateTime desde, OffsetDateTime hasta) {

        WebClient webClient = webClientBuilder.baseUrl(this.getUrl(backendServer, backendPort)).build();

        log.debug("Requesting comprobantes de ventas from external service: {}", getUrl(backendServer, backendPort));

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/core/clienteMovimiento/arca/regimen/informacion/ventas/{desde}/{hasta}")
                        .build(desde, hasta))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("Error from external service. Status: {}, Body: {}", response.statusCode(), errorBody);
                                    return Mono.error(new NegocioException("Error calling clientemovimiento service: " + errorBody));
                                })
                )
                .bodyToFlux(NegocioCoreClienteMovimiento.class)
                .collectList()
                .doOnSuccess(values -> log.debug("Received values: {}", values))
                .doOnError(error -> log.error("Failed to retrieve clientemovimiento. Reason: {}", error.getMessage()));
    }

}
