package com.socialsync.gateway.filtering;


import com.socialsync.gateway.model.AuthorizationDto;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomGlobalFilter {
    private String baseUrl = "http://localhost:8087/api/v1/authorization";
    RestTemplate restTemplate = new RestTemplate();

    @Bean
    public GlobalFilter customFilter() {
        return (exchange, chain) -> {
            // parametri
            String requestUri = exchange.getRequest().getURI().toString();
            HttpMethod requestMethod = exchange.getRequest().getMethod();
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            String token = "";
            if (authorizationHeader != null) {
                token = authorizationHeader.replace("Bearer ", "");
            }
            List<PathContainer.Element> pathElements = exchange.getRequest().getPath().elements();

            String url = baseUrl;

            try {
                restTemplate.postForObject(url, new AuthorizationDto(requestUri, requestMethod.name(), token), Void.class);
                System.out.println("Cerere cu succes!");
                return chain.filter(exchange);
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    response.getHeaders().add("Content-Type", "application/json");
                    String responseBody = "{\"error\": \"Unauthorized\"}";
                    return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBody.getBytes())));
                } else {
                    System.out.println("Eroare: " + e.getRawStatusCode() + " " + e.getStatusText());
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    response.getHeaders().add("Content-Type", "application/json");
                    String responseBody = "{\"error\": \"Eroare\"}";
                    return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBody.getBytes())));
                }
            }
        };
    }
}