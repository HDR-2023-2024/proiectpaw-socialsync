package com.socialsync.gateway.filtering;

import com.socialsync.gateway.other.Pair;
import org.bouncycastle.asn1.ocsp.Request;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomGlobalFilter {
    @Bean
    public GlobalFilter customFilter() {
        return (exchange, chain) -> {
            // url
            String requestUri = exchange.getRequest().getURI().toString();
            HttpMethod requestMethod = exchange.getRequest().getMethod();

            List<Pair<String, HttpMethod>> publicUrl =  new ArrayList<>(List.of(
                    new Pair<>("/api/v1/posts/{id}",HttpMethod.GET),
                    new Pair<>("/api/v1/posts",HttpMethod.GET),
                    new Pair<>("/api/v1/comments/{id}",HttpMethod.GET),
                    new Pair<>("/api/v1/comments",HttpMethod.GET),
                    new Pair<>("/api/v1/users/{id}",HttpMethod.GET),
                    new Pair<>("/api/v1/users",HttpMethod.GET),
                    new Pair<>("/api/v1/users",HttpMethod.POST),
                    new Pair<>("/api/v1/topics/{id}",HttpMethod.GET),
                    new Pair<>("/api/v1/topics",HttpMethod.GET)
            ));


            System.out.println("Executing custom filter for route: " + requestUri);

            for (Pair<String, HttpMethod> pair : publicUrl) {
                if (requestUri.contains(pair.getKey()) && pair.getValue() == requestMethod) {
                    return chain.filter(exchange);
                }
            }

            //return chain.filter(exchange);
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "application/json");
            String responseBody = "{\"error\": \"Unauthorized\"}";
            return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBody.getBytes())));
        };
    }
}