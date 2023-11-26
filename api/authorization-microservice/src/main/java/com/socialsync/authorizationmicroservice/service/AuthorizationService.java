package com.socialsync.authorizationmicroservice.service;

import com.socialsync.authorizationmicroservice.interfaces.AuthorizationServiceMethods;
import com.socialsync.authorizationmicroservice.pojo.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthorizationService implements AuthorizationServiceMethods {
    RestTemplate restTemplate = new RestTemplate();

    public boolean login(UserDto userDto){
        return false;
    }

}
