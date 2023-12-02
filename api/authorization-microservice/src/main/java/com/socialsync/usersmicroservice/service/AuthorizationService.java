package com.socialsync.usersmicroservice.service;

import com.socialsync.usersmicroservice.exceptions.UnauthorizedException;
import com.socialsync.usersmicroservice.model.AuthorizationDto;
import com.socialsync.usersmicroservice.other.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class AuthorizationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);
    private List<Pair<String, String>> publicUrl = new ArrayList<>(List.of(
            new Pair<>("/api/v1/posts/{id}", "GET"),
            new Pair<>("/api/v1/posts", "GET"),
            new Pair<>("/api/v1/comments/{id}", "GET"),
            new Pair<>("/api/v1/comments", "GET"),
            new Pair<>("/api/v1/users/{id}", "GET"), // ?
            //new Pair<>("/api/v1/users","GET"),    // get all doar adminul
            new Pair<>("/api/v1/users", "POST"),
            new Pair<>("/api/v1/topics/{id}", "GET"),
            new Pair<>("/api/v1/topics", "GET")
    ));

    private List<Pair<String, String>> userURL = new ArrayList<>(List.of(
            new Pair<>("/api/v1/posts", "POST"),
            new Pair<>("/api/v1/posts/{id}", "PUT"),
            new Pair<>("/api/v1/posts/{id}", "DELETE"),
            new Pair<>("/api/v1/comments", "POST"),
            new Pair<>("/api/v1/comments/{id}", "PUT"),
            new Pair<>("/api/v1/comments/{id}", "DELETE"),
            new Pair<>("/api/v1/users/{id}", "PUT"),
            new Pair<>("/api/v1/users/{id}", "DELETE"),
            new Pair<>("/api/v1/topics", "POST"),
            new Pair<>("/api/v1/topics/{id}", "PUT"),
            new Pair<>("/api/v1/topics/{id}", "DELETE")
    ));

    RestTemplate restTemplate = new RestTemplate();

    public boolean isAuthorized(AuthorizationDto authorizationDto) throws UnauthorizedException {
        System.out.println(authorizationDto);

        for (Pair<String, String> obj : publicUrl) {
            if (authorizationDto.getUrl().contains(obj.getKey().replace("{id}", "")) && Objects.equals(obj.getValue(), authorizationDto.getMethod())) {
                return true;
            }
        }

        if (authorizationDto.getToken() == null || authorizationDto.getToken().equals("")) {
            logger.error("Token-ul este necesar dar valoarea data pentru el este null sau vida");
            throw new UnauthorizedException("");
        }
        HttpEntity<String> requestEntity = new HttpEntity<>(authorizationDto.getToken());
        try {
            String url = "http://localhost:8082/api/v1/users/validateJWT";
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            String responseBody = responseEntity.getBody();
            System.out.println("ID-ul celui ce a facut cererea este: " + responseBody);
        } catch (HttpClientErrorException e) {
            logger.error("A aparut o exceptie la vallidarea token-ului, nu se permite accesul: " + e.getMessage());
            throw new UnauthorizedException("");
        }
        return false;
    }
}
