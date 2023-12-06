package com.socialsync.gateway.filter;

import com.socialsync.gateway.filter.exceptions.UnauthorizedException;
import com.socialsync.gateway.filter.model.AuthorizedInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AuthorizationService {
    private RestTemplate restTemplate = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    public AuthorizedInfo authorized(String token) throws UnauthorizedException {
        if (token.contains("Bearer ")) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, token.replace("Bearer ",""));

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            try {
                String url = "http://authorization-microservice:8087/api/v1/authorization/validateJWT";
                ResponseEntity<AuthorizedInfo> responseEntity = restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        requestEntity,
                        AuthorizedInfo.class
                );
                return responseEntity.getBody();
            } catch (HttpClientErrorException e) {
                logger.error("A aparut o exceptie la validarea token-ului, nu se permite accesul: " + e.getMessage());
                throw new UnauthorizedException();
            }
        } else {
            throw new UnauthorizedException("Tokenul nu contine antetul 'Bearer'.");
        }
    }
}

