package com.socialsync.usersmicroservice.api;

import com.socialsync.usersmicroservice.pojo.*;
import com.socialsync.usersmicroservice.service.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/authorization")
@AllArgsConstructor
public class AuthorizationController {
    private final JWTService jwtService;

    @PostMapping("/generateJWT")
    public ResponseEntity<?> getJWT(@RequestBody AuthorizedInfo authorizedInfo) {
    System.out.println(authorizedInfo.getId() + " " + authorizedInfo.getRole());
        try {
            String token = jwtService.generateAccessToken(authorizedInfo.getId(),authorizedInfo.getRole());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization",  token);
            
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(authorizedInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/validateJWT")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null ) {
            System.out.println("Cerere cu token-ul: " + authorizationHeader);
            try {
                AuthorizedInfo result = this.jwtService.decodeToken(authorizationHeader);
                System.out.println(result.getId() + " " + result.getRole());
                return new ResponseEntity<>(result, HttpStatus.OK);
            }catch (io.jsonwebtoken.ExpiredJwtException ex){
                System.out.print(ex.getMessage());
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            System.out.println("Lipsa header");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
