package com.diegonunez.Inventory_MS.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${product.api.base-url}")
    private String productBaseUrl;

    @Value("${security.api-key}")
    private String productApiKey;

    @Bean
    public RestTemplate restTemplate() {
        // Connection manager con pool y timeout
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(5);
        connectionManager.setMaxTotal(20);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .evictIdleConnections(TimeValue.ofSeconds(30)) // Evict connections after idle
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(5000);     // Tiempo máximo de conexión en ms
        factory.setReadTimeout(5000);        // Tiempo máximo para leer la respuesta
        factory.setConnectionRequestTimeout(5000); // Tiempo para pedir conexión del pool

        RestTemplate restTemplate = new RestTemplate(factory);

        // Interceptor para enviar la API Key
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("X-API-KEY", productApiKey);
            return execution.execute(request, body);
        });

        return restTemplate;
    }
}
