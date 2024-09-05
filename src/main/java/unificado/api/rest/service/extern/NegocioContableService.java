package unificado.api.rest.service.extern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@Slf4j
public class NegocioContableService {

    private String getUrl(String server, String port) {
        return "http://" + server + ":" + port;
    }

    public List<BigDecimal> totalesEntreFechasByNegocio(String backendServer, String backendPort, Long numeroCuenta, OffsetDateTime desde, OffsetDateTime hasta, Boolean incluyeApertura, Boolean incluyeInflacion) {
        String url = this.getUrl(backendServer, backendPort) + "/api/core/balance";
        WebClient webClient = WebClient.builder().baseUrl(url).build();
        String request = "/totalesEntreFechas/" + numeroCuenta + "/" + desde + "/" + hasta + "/" + incluyeApertura + "/" + incluyeInflacion;
        log.debug("url={}", url);
        List<BigDecimal> values = webClient.get()
                .uri(request)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(BigDecimal.class)
                .collectList()
                .block();
        log.debug("values={}", values);
        return values;
    }
}
