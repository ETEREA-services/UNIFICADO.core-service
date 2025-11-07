package unificado.core.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/unificado.properties")
public class UnificadoConfiguration {
}
