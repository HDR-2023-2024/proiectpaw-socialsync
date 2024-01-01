package com.socialsync.usersmicroservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.AMQP;
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
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final AuthorizationService authorizationService;
    private final ValidateParameterService validateParameterService;

    @GetMapping
    public ResponseEntity<?> fetchAllUsers(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole) {
        if (Objects.equals(userRole, "admin")) {
            return new ResponseEntity<>(usersService.fetchAllUsers(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Access allowed only for admin!", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> fetchUserById(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole, @PathVariable String id) {
        try {
            if (Objects.equals(userRole, "admin") || Objects.equals(userId, id)) {
                return new ResponseEntity<>(usersService.fetchUserById(id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Access allowed only for admin or the user who has this id!", HttpStatus.UNAUTHORIZED);
            }
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            if (!validateParameterService.isValidEmail(user.getEmail())) {
                return new ResponseEntity<>("Adresa da email nu are un format valid!", HttpStatus.NOT_ACCEPTABLE);
            }
            if (!validateParameterService.isValidPassword(user.getPassword())) {
                return new ResponseEntity<>("Parola poate contine doar caractere alfanumerice si caracterul \".\"", HttpStatus.NOT_ACCEPTABLE);
            }
            if (!validateParameterService.isValidUsername(user.getUsername())) {
                return new ResponseEntity<>("Numele de utilizator poate contine doar caractere alfanumerice si caracterul \".\"", HttpStatus.NOT_ACCEPTABLE);
            }
            usersService.addUser(user);
            String token = authorizationService.getJwt(new AuthorizedInfo(user.getId(), user.getRole().name()));
            return new ResponseEntity<>(new AuthorizedResponseDto("Bearer " + token,user.getUsername(),user.getPhotoId(),user.getId()),HttpStatus.OK);
        } catch (NotAcceptableException unauthorizedException) {
            return new ResponseEntity<>(unauthorizedException.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updateUser(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole, @RequestBody UserSelect user) {
        if (!validateParameterService.isValidEmail(user.getEmail())) {
            return new ResponseEntity<>("Adresa da email nu are un format valid!", HttpStatus.NOT_ACCEPTABLE);
        }
        if (!validateParameterService.isValidUsername(user.getUsername())) {
            return new ResponseEntity<>("Numele de utilizator poate contine doar caractere alfanumerice si caracterul \".\"", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            System.out.println(user);
            usersService.updateUser(userId, user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole, @PathVariable String id) {
        try {
            if (Objects.equals(userRole, "admin") || Objects.equals(userId, id)) {
                usersService.deleteUser(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Access allowed only for admin or the user who has this id!", HttpStatus.UNAUTHORIZED);
            }
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials credentials) {
        if (!validateParameterService.isValidPassword(credentials.getPassword())) {
            return new ResponseEntity<>("Parola poate contine doar caractere alfanumerice si caracterul \".\"", HttpStatus.NOT_ACCEPTABLE);
        }
        if (!validateParameterService.isValidUsername(credentials.getUsername())) {
            return new ResponseEntity<>("Numele de utilizator poate contine doar caractere alfanumerice si caracterul \".\"", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            AuthorizedInfo authorizedInfo = usersService.login(credentials);
            String token = authorizationService.getJwt(authorizedInfo);
           UserSelect user = this.usersService.fetchUserById(authorizedInfo.getId());
            return new ResponseEntity<>(new AuthorizedResponseDto("Bearer " + token,user.getUsername(),user.getPhotoId(),user.getId()) ,HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Datele de autentificare nu sunt valide!", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole, @RequestBody Password password) {
        // autorizare
        if (!validateParameterService.isValidPassword(password.getPassword())) {
            return new ResponseEntity<>("Parola poate contine doar caractere alfanumerice si caracterul \".\". Lungimea de minim 5 caractere", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            usersService.updatePassword(userId, password.getPassword());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/sendCodeResetPassword")
    public ResponseEntity<?> sendCodeResetPassword(@RequestBody EmailDto emailDto) throws JsonProcessingException {
        if(this.usersService.sendCodeResetPassword(emailDto.getEmail())){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/validateCode")
    public ResponseEntity<?> validateCode(@RequestBody ValidateCode validateCode) {
        try {
            AuthorizedResponseDto s = this.usersService.validateCode(validateCode);

            return new ResponseEntity<>(s, HttpStatus.OK);
        }catch (UnauthorizedException unauthorizedException){
            return new ResponseEntity<>("Codul de sutentificare este invalid", HttpStatus.UNAUTHORIZED);
        }
    }
}
