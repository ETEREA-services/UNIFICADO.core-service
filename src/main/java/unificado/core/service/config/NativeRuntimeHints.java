package unificado.core.service.config;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import unificado.core.service.model.Auditable;
import unificado.core.service.model.Cuenta;
import unificado.core.service.model.CuentaNegocio;
import unificado.core.service.model.Negocio;

import java.math.BigDecimal;

public class NativeRuntimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.reflection().registerType(Cuenta.class);
        hints.reflection().registerType(CuentaNegocio.class);
        hints.reflection().registerType(Negocio.class);
        hints.reflection().registerType(Auditable.class);
        hints.reflection().registerType(BigDecimal.class);
    }
}
