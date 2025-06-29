package PFE.CDSIR_AGENCY.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

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

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Configuration globale pour toutes les routes
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods(allowedMethods)
                        .allowedHeaders(allowedHeaders)
                        .exposedHeaders(exposedHeaders)
                        .allowCredentials(true)
                        .maxAge(maxAge);

                // Configuration spécifique pour les WebSockets si nécessaire
                registry.addMapping("/ws/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods(allowedMethods)
                        .allowedHeaders(allowedHeaders)
                        .allowCredentials(true)
                        .maxAge(maxAge);
            }
        };
    }

    /**
     * Méthode pour vérifier la configuration CORS actuelle
     */
    public String printCorsConfiguration() {
        return String.format(
                "CORS Configuration:\n" +
                        "Allowed Origins: %s\n" +
                        "Allowed Methods: %s\n" +
                        "Allowed Headers: %s\n" +
                        "Exposed Headers: %s\n" +
                        "Max Age: %d seconds\n" +
                        "Credentials Allowed: true",
                Arrays.toString(allowedOrigins),
                Arrays.toString(allowedMethods),
                Arrays.toString(allowedHeaders),
                Arrays.toString(exposedHeaders),
                maxAge
        );
    }

    /**
     * Méthode utilitaire pour vérifier si une origine est autorisée
     */
    public boolean isOriginAllowed(String origin) {
        return Arrays.asList(allowedOrigins).contains(origin);
    }

    /**
     * Méthode utilitaire pour vérifier si une méthode est autorisée
     */
    public boolean isMethodAllowed(String method) {
        return Arrays.asList(allowedMethods).contains(method.toUpperCase());
    }
}