package unificado.core.service.service.extern;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import unificado.core.service.exception.NegocioException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

class NegocioContableServiceTest {

    private static MockWebServer mockWebServer;
    private NegocioContableService negocioContableService;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void initialize() {
        WebClient.Builder webClientBuilder = WebClient.builder();
        negocioContableService = new NegocioContableService(webClientBuilder);
    }

    @Test
    void whenTotalesEntreFechas_andServerReturns200_thenReturnsTotals() {
        // Given
        mockWebServer.enqueue(new MockResponse()
                .setBody("[100.50, 75.25]")
                .addHeader("Content-Type", "application/json"));

        String server = mockWebServer.getHostName();
        int port = mockWebServer.getPort();

        // When
        Mono<List<BigDecimal>> result = negocioContableService.totalesEntreFechasByNegocio(
                server, String.valueOf(port), 1L, OffsetDateTime.now(), OffsetDateTime.now().plusDays(1), true, false);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(list ->
                        list.size() == 2 &&
                        list.get(0).compareTo(new BigDecimal("100.50")) == 0 &&
                        list.get(1).compareTo(new BigDecimal("75.25")) == 0)
                .verifyComplete();
    }

    @Test
    void whenTotalesEntreFechas_andServerReturnsError_thenThrowsNegocioException() {
        // Given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error"));

        String server = mockWebServer.getHostName();
        int port = mockWebServer.getPort();

        // When
        Mono<List<BigDecimal>> result = negocioContableService.totalesEntreFechasByNegocio(
                server, String.valueOf(port), 1L, OffsetDateTime.now(), OffsetDateTime.now().plusDays(1), true, false);

        // Then
        StepVerifier.create(result)
                .expectError(NegocioException.class)
                .verify();
    }
}
