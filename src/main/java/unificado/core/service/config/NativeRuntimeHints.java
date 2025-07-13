package unificado.core.service.config;

import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import unificado.core.service.model.Auditable;
import unificado.core.service.model.Cuenta;
import unificado.core.service.model.CuentaNegocio;
import unificado.core.service.model.Negocio;

import java.math.BigDecimal;

@Configuration
public class NativeRuntimeHints {

    @Bean
    public RuntimeHintsRegistrar runtimeHintsRegistrar() {
        return (hints, classLoader) -> {
            hints.reflection().registerType(Cuenta.class);
            hints.reflection().registerType(CuentaNegocio.class);
            hints.reflection().registerType(Negocio.class);
            hints.reflection().registerType(Auditable.class);
            hints.reflection().registerType(BigDecimal.class);
        };
    }
}
