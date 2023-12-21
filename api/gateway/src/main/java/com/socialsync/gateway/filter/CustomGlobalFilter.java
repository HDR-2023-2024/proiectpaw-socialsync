package com.socialsync.gateway.filter;

import com.socialsync.gateway.filter.exceptions.UnauthorizedException;
import com.socialsync.gateway.filter.model.AuthorizedInfo;
import com.socialsync.gateway.filter.model.Pair;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CustomGlobalFilter {
    private final AuthorizationService authorizationService;

    @Bean
    public GlobalFilter customFilter() {
        return (exchange, chain) -> {
            // parametri
            String requestUri = exchange.getRequest().getURI().toString();
            HttpMethod requestMethod = exchange.getRequest().getMethod();
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            List<Pair<String, HttpMethod>> publicUrl = new ArrayList<>(List.of(
                    new Pair<>("/api/v1/posts/{id}", HttpMethod.GET),
                    new Pair<>("/api/v1/posts", HttpMethod.GET),
                    new Pair<>("/api/v1/comments/{id}", HttpMethod.GET),
                    new Pair<>("/api/v1/comments", HttpMethod.GET),
                    new Pair<>("/api/v1/users", HttpMethod.POST),
                    new Pair<>("/api/v1/users/login", HttpMethod.POST),
                    new Pair<>("/api/v1/topics/{id}", HttpMethod.GET),
                    new Pair<>("/api/v1/topics", HttpMethod.GET),
                    new Pair<>("/query/", HttpMethod.GET),
                    new Pair<>("/api/v1/storage/", HttpMethod.GET),
                    //new Pair<>("/api/v1/storage/", HttpMethod.POST),
                    new Pair<>("/notification/send/",HttpMethod.POST)
            ));

            System.out.println("Executing custom filter for route: " + requestUri);

            for (Pair<String, HttpMethod> pair : publicUrl) {
                if (requestUri.contains(pair.getKey()) && pair.getValue() == requestMethod) {
                    if(authorizationHeader != null){
                        try{
                            AuthorizedInfo authorizedInfo = authorizationService.authorized(authorizationHeader);

                            String userId = authorizedInfo.getId();
                            String role = authorizedInfo.getRole();

                            ServerHttpRequest mutatedRequest = exchange.getRequest()
                                    .mutate()
                                    .header("X-User-Id", userId)
                                    .header("X-User-Role", role)
                                    .build();
                            System.out.println("Cerere pentru utilizatorul: " + userId);
                            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

                            // continuare
                            return chain.filter(mutatedExchange);
                        }catch (UnauthorizedException un)
                        {
                            return chain.filter(exchange);
                        }
                    }else{
                        return chain.filter(exchange);
                    }
                }
            }

            try {
                AuthorizedInfo authorizedInfo = authorizationService.authorized(authorizationHeader);

                String userId = authorizedInfo.getId();
                String role = authorizedInfo.getRole();

                ServerHttpRequest mutatedRequest = exchange.getRequest()
                        .mutate()
                        .header("X-User-Id", userId)
                        .header("X-User-Role", role)
                        .build();
                System.out.println("Cerere pentru utilizatorul: " + userId);
                ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

                // continuare
                return chain.filter(mutatedExchange);
            } catch (UnauthorizedException unauthorizedException) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type", "application/json");
                String responseBody = "{\"error\": \"Unauthorized\"}";
                return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBody.getBytes())));
            }
        };
    }
}