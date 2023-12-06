package com.socialsync.commentsmicroservice.service;

import com.socialsync.commentsmicroservice.pojo.AuthorizedInfo;
import com.socialsync.commentsmicroservice.util.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthorizationService {
    private RestTemplate restTemplate = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    public AuthorizedInfo authorized(String token) throws UnauthorizedException {
        if(token.contains("Bearer ")) {
            HttpEntity<String> requestEntity = new HttpEntity<>(token.replace("Bearer ",""));
            try {
                String url = "http://users-microservice:8082/api/v1/users/validateJWT";
                ResponseEntity<AuthorizedInfo> responseEntity = restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        requestEntity,
                        AuthorizedInfo.class
                );
                return responseEntity.getBody();
            } catch (HttpClientErrorException e) {
                logger.error("A aparut o exceptie la vallidarea token-ului, nu se permite accesul: " + e.getMessage());
                throw new UnauthorizedException();
            }
        }else{
            throw new UnauthorizedException("The token has no Bearer header");
        }
    }
}
