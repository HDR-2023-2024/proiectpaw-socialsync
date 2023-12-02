package com.socialsync.usersmicroservice.api;

import com.socialsync.usersmicroservice.exceptions.NotAcceptableException;
import com.socialsync.usersmicroservice.exceptions.UnauthorizedException;
import com.socialsync.usersmicroservice.pojo.*;
import com.socialsync.usersmicroservice.service.AuthorizationService;
import com.socialsync.usersmicroservice.service.UsersService;
import com.socialsync.usersmicroservice.service.ValidateParameterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final AuthorizationService authorizationService;
    private final ValidateParameterService validateParameterService;

    @GetMapping
    public ResponseEntity<?> fetchAllUsers(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            AuthorizedInfo authorizedInfo = authorizationService.authorized(authorizationHeader);
            if (Objects.equals(authorizedInfo.getRole(), "admin")) {
                return new ResponseEntity<>(usersService.fetchAllUsers(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Access allowed only for admin!", HttpStatus.UNAUTHORIZED);
            }
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> fetchUserById(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String id) {
        try {
            AuthorizedInfo authorizedInfo = authorizationService.authorized(authorizationHeader);
            if (Objects.equals(authorizedInfo.getRole(), "admin") || Objects.equals(authorizedInfo.getId(), id)) {
                return new ResponseEntity<>(usersService.fetchUserById(id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Access allowed only for admin or the user who has this id!", HttpStatus.UNAUTHORIZED);
            }
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            if(!validateParameterService.isValidEmail(user.getEmail())){
                return new ResponseEntity<>("Adresa da email nu are un format valid!",HttpStatus.NOT_ACCEPTABLE);
            }
            if(!validateParameterService.isValidPassword(user.getPassword())){
                return new ResponseEntity<>("Parola poate contine doar caractere alfanumerice si caracterul \".\"",HttpStatus.NOT_ACCEPTABLE);
            }
            if(!validateParameterService.isValidUsername(user.getUsername())){
                return new ResponseEntity<>("Numele de utilizator poate contine doar caractere alfanumerice si caracterul \".\"",HttpStatus.NOT_ACCEPTABLE);
            }
            usersService.addUser(user);
            String token = usersService.generateJWT(user.getId());
            HttpHeaders responseHeaders = new HttpHeaders();
            //Authorization: Bearer token
            responseHeaders.set("Authorization", "Bearer " + token);
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(new UserSelect(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getGender()));
        } catch (NotAcceptableException unauthorizedException) {
            return new ResponseEntity<>(unauthorizedException.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UserSelect user) {
        if(!validateParameterService.isValidEmail(user.getEmail())){
            return new ResponseEntity<>("Adresa da email nu are un format valid!",HttpStatus.NOT_ACCEPTABLE);
        }
        if(!validateParameterService.isValidUsername(user.getUsername())){
            return new ResponseEntity<>("Numele de utilizator poate contine doar caractere alfanumerice si caracterul \".\"",HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            AuthorizedInfo authorizedInfo = authorizationService.authorized(authorizationHeader);
            if (Objects.equals(authorizedInfo.getId(), authorizedInfo.getId())) {
                usersService.updateUser(authorizedInfo.getId(), user);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Access allowed only for user who has this id!", HttpStatus.UNAUTHORIZED);
            }
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String id) {
        try {
            AuthorizedInfo authorizedInfo = authorizationService.authorized(authorizationHeader);
            if (Objects.equals(authorizedInfo.getRole(), "admin") || Objects.equals(authorizedInfo.getId(), id)) {
                usersService.deleteUser(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Access allowed only for admin or the user who has this id!", HttpStatus.UNAUTHORIZED);
            }
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials credentials) {
        if(!validateParameterService.isValidPassword(credentials.getPassword())){
            return new ResponseEntity<>("Parola poate contine doar caractere alfanumerice si caracterul \".\"",HttpStatus.NOT_ACCEPTABLE);
        }
        if(!validateParameterService.isValidUsername(credentials.getUsername())){
            return new ResponseEntity<>("Numele de utilizator poate contine doar caractere alfanumerice si caracterul \".\"",HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            AuthorizedInfo authorizedInfo = usersService.login(credentials);
            String token = usersService.generateJWT(authorizedInfo.getId());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", "Bearer " + token);
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(authorizedInfo);
        } catch (Exception ex) {
            return new ResponseEntity<>("Datele de autentificare nu sunt valide!", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Password password) {
        // autorizare
        if(!validateParameterService.isValidPassword(password.getPassword())){
            return new ResponseEntity<>("Parola poate contine doar caractere alfanumerice si caracterul \".\". Lungimea de minim 5 caractere",HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            AuthorizedInfo authorizedInfo = authorizationService.authorized(authorizationHeader);
            usersService.updatePassword(authorizedInfo.getId(), password.getPassword());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/validateJWT")
    public ResponseEntity<?> validateToken(@RequestBody String token) {
        System.out.println("Cerere cu token-ul: " + token);
        try {
            AuthorizedInfo result = this.usersService.isValidJWT(token);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
