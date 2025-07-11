package unificado.core.service.service.extern;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import unificado.core.service.exception.NegocioException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NegocioContableService {

    // Inject the WebClient.Builder, which is auto-configured by Spring Boot
    // and is the factory for creating client instances.
    private final WebClient.Builder webClientBuilder;

    private String getUrl(String server, String port) {
        return "http://" + server + ":" + port;
    }

    public Mono<List<BigDecimal>> totalesEntreFechasByNegocio(
            String backendServer,
            String backendPort,
            Long numeroCuenta,
            OffsetDateTime desde,
            OffsetDateTime hasta,
            Boolean incluyeApertura,
            Boolean incluyeInflacion) {

        // Create a specific WebClient instance for this request with the dynamic URL.
        // This is a lightweight operation that reuses underlying resources.
        WebClient webClient = webClientBuilder.baseUrl(this.getUrl(backendServer, backendPort)).build();

        log.debug("Requesting totales from external service at {}/api/core/balance for account: {}", getUrl(backendServer, backendPort), numeroCuenta);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/core/balance/totalesEntreFechas/{numeroCuenta}/{desde}/{hasta}/{incluyeApertura}/{incluyeInflacion}")
                        .build(numeroCuenta, desde, hasta, incluyeApertura, incluyeInflacion))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("Error from external service. Status: {}, Body: {}", response.statusCode(), errorBody);
                                    return Mono.error(new NegocioException("Error calling contable service: " + errorBody));
                                })
                )
                .bodyToFlux(BigDecimal.class)
                .collectList()
                .doOnSuccess(values -> log.debug("Received values: {}", values))
                .doOnError(error -> log.error("Failed to retrieve totales for account {}", numeroCuenta, error));
    }
}
