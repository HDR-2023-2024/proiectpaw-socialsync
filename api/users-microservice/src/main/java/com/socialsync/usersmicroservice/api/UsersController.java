package com.socialsync.usersmicroservice.api;

import com.socialsync.usersmicroservice.pojo.Credentials;
import com.socialsync.usersmicroservice.pojo.User;
import com.socialsync.usersmicroservice.pojo.UserSelect;
import com.socialsync.usersmicroservice.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    public ResponseEntity<HashMap<String, UserSelect>> fetchAllUsers() {
        return new ResponseEntity<>(usersService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> fetchUserById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(usersService.fetchUserById(id), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<UserSelect> addUser(@RequestBody User user) {
        usersService.addUser(user);
        String token = usersService.generateJWT(user.getId());
        HttpHeaders responseHeaders = new HttpHeaders();
        //Authorization: Bearer token
        responseHeaders.set("Authorization", "Bearer " + token);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(new UserSelect(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getGender()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserSelect user) {
        try {
            usersService.updateUser(id, user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        usersService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials credentials) {
        User user = usersService.login(credentials);
        if (user != null) {
            String token = usersService.generateJWT(user.getId());
            HttpHeaders responseHeaders = new HttpHeaders();
            //Authorization: Bearer token
            responseHeaders.set("Authorization",
                    "Bearer " + token);

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(new UserSelect(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getGender()));
        } else {
            return new ResponseEntity<>("Datele de autentificare nu sunt valide!", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable String id,@RequestBody String password) {
        usersService.updatePassword(id,password);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validateJWT")
    public ResponseEntity<?> validateToken(@RequestBody String token) {
        try {
            String result = this.usersService.isValidJWT(token);
            if (result != null) {
                return new ResponseEntity<>(result,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/testMethod")
    public ResponseEntity<?> testToken() {
        try {
            String token = usersService.generateJWT("12334");
            //token += "a";
            String ok = usersService.isValidJWT(token);
            System.out.println("Id la validate: " + ok);
            if(Objects.equals(ok, "12334")){
                return  new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
