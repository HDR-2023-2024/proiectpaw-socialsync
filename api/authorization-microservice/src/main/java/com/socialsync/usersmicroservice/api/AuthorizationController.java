package com.socialsync.usersmicroservice.api;

import com.socialsync.usersmicroservice.exceptions.UnauthorizedException;
import com.socialsync.usersmicroservice.model.AuthorizationDto;
import com.socialsync.usersmicroservice.service.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/authorization")
@AllArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity<?> authorization(@RequestBody AuthorizationDto authorizationDto) throws UnauthorizedException {
        try {
            boolean isAuthorized = authorizationService.isAuthorized(authorizationDto);
            if (isAuthorized) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }catch (UnauthorizedException unauthorizedException){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
