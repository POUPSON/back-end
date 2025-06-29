package PFE.CDSIR_AGENCY.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Value("${app.cors.allowed-methods}")
    private String[] allowedMethods;

    @Value("${app.cors.allowed-headers}")
    private String[] allowedHeaders;

    @Value("${app.cors.exposed-headers}")
    private String[] exposedHeaders;

    @Value("${app.cors.max-age}")
    private long maxAge;

    @Value("${app.cors.allow-credentials:true}")
    private boolean allowCredentials;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Appliquer CORS sur toutes les routes
                registry.addMapping("/**")
                    .allowedOrigins(allowedOrigins)
                    .allowedMethods(allowedMethods)
                    .allowedHeaders(allowedHeaders)
                    .exposedHeaders(exposedHeaders)
                    .allowCredentials(allowCredentials)
                    .maxAge(maxAge);

                // Optionnel : si tu as une API WebSocket (exemple ici)
                registry.addMapping("/ws/**")
                    .allowedOrigins(allowedOrigins)
                    .allowedMethods(allowedMethods)
                    .allowedHeaders(allowedHeaders)
                    .allowCredentials(allowCredentials)
                    .maxAge(maxAge);
            }
        };
    }
}
