package com.socialsync.usersmicroservice.api;

import com.google.gson.Gson;
import com.socialsync.usersmicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.usersmicroservice.pojo.User;
import com.socialsync.usersmicroservice.pojo.UserQueueMessage;
import com.socialsync.usersmicroservice.pojo.UserSelect;
import com.socialsync.usersmicroservice.pojo.enums.QueueMessageType;
import com.socialsync.usersmicroservice.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        usersService.addUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            usersService.updateUser(id, user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        usersService.deleteUser(id);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }


}
