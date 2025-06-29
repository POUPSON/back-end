package PFE.CDSIR_AGENCY.config;

import PFE.CDSIR_AGENCY.exception.PaymentErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateConfig.class);

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(30))
                .additionalInterceptors(
                        new LoggingInterceptor(),
                        new RetryInterceptor()
                )
                .errorHandler(new PaymentErrorHandler()) // Utilisation corrigée
                .build();
    }

    private static class LoggingInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            log.debug("Requête envoyée : {} {}", request.getMethod(), request.getURI());
            return execution.execute(request, body);
        }
    }

    private static class RetryInterceptor implements ClientHttpRequestInterceptor {
        private final int maxRetries = 3;

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            int retryCount = 0;
            ClientHttpResponse response;
            do {
                response = execution.execute(request, body);
                retryCount++;
            } while (!response.getStatusCode().is2xxSuccessful() && retryCount < maxRetries);
            return response;
        }
    }
}