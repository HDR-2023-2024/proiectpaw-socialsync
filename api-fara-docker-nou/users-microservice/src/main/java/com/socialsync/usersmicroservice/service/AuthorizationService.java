package com.socialsync.usersmicroservice.service;

import com.socialsync.usersmicroservice.exceptions.UnauthorizedException;
import com.socialsync.usersmicroservice.pojo.AuthorizedInfo;
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

    public String getJwt(AuthorizedInfo authorizedInfo) throws UnauthorizedException {
        HttpEntity<AuthorizedInfo> requestEntity = new HttpEntity<>(authorizedInfo);
        try {
            String url = "http://localhost:8087/api/v1/authorization/generateJWT";
            ResponseEntity<Void> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Void.class
            );

            HttpHeaders headers = responseEntity.getHeaders();
            List<String> authorizationHeader = headers.get(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
                return authorizationHeader.get(0);
            } else {
                throw new UnauthorizedException("Tokenul JWT lipseste.");
            }
        } catch (HttpClientErrorException e) {
            logger.error("A aparut o excwptie la generarea tokenului: " + e.getMessage());
            throw new UnauthorizedException();
        }
    }
}

