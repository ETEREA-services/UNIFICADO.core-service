package unificado.core.service.extern.negocio.core.proveedormovimiento;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import unificado.core.service.extern.negocio.core.proveedormovimiento.domain.NegocioCoreProveedorMovimiento;
import unificado.core.service.extern.negocio.core.proveedormovimiento.domain.dto.ProveedorMovimientoNetoAjusteRequestDto;
import unificado.core.service.negocio.exception.NegocioException;
import unificado.core.service.util.AjusteNeto;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NegocioCoreProveedorMovimientoService {

    private final WebClient.Builder webClientBuilder;

    private String getUrl(String server, String port) {
        return "http://" + server + ":" + port;
    }

    public Mono<List<NegocioCoreProveedorMovimiento>> findAllInformacionCompras(String backendServer,
                                                                                String backendPort, OffsetDateTime desde, OffsetDateTime hasta) {

        WebClient webClient = webClientBuilder.baseUrl(this.getUrl(backendServer, backendPort)).build();

        log.debug("Requesting comprobantes de compras from external service: {}", getUrl(backendServer, backendPort));

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/core/proveedormovimiento/arca/regimen/informacion/compras/{desde}/{hasta}")
                        .build(desde, hasta))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            log.error("Error from external service. Status: {}, Body: {}", response.statusCode(),
                                    errorBody);
                            return Mono.error(
                                    new NegocioException("Error calling proveedormovimiento service: " + errorBody));
                        }))
                .bodyToFlux(NegocioCoreProveedorMovimiento.class)
                .collectList()
                // .doOnSuccess(values -> log.debug("Received values: {}", values))
                .doOnError(
                        error -> log.error("Failed to retrieve proveedormovimiento. Reason: {}", error.getMessage()));
    }

    public Mono<NegocioCoreProveedorMovimiento> adjustNetoAndIvas(String backendServer, String backendPort,
                                                                  Long proveedorMovimientoId, AjusteNeto netoAjustado) {
        var request = ProveedorMovimientoNetoAjusteRequestDto.builder()
                .proveedorMovimientoId(proveedorMovimientoId)
                .netoAjustado(netoAjustado.getNeto())
                .montoIva21Ajustado(netoAjustado.getMontoIva21())
                .montoIva105Ajustado(netoAjustado.getMontoIva105())
                .montoIva27Ajustado(netoAjustado.getMontoIva27())
                .build();

        log.debug("Adjusting neto and ivas via external service: {}", getUrl(backendServer, backendPort));

        return webClientBuilder.baseUrl(this.getUrl(backendServer, backendPort)).build()
                .put()
                .uri("/api/core/proveedormovimiento/neto/ajuste/arca/")
                .bodyValue(request)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            log.error("Error from external service. Status: {}, Body: {}", response.statusCode(),
                                    errorBody);
                            return Mono.error(new NegocioException(
                                    "Error calling proveedormovimiento service adjustment: " + errorBody));
                        }))
                .bodyToMono(NegocioCoreProveedorMovimiento.class)
                .doOnError(error -> log.error("Failed to adjust proveedormovimiento. Reason: {}", error.getMessage()));
    }

}
