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
    private RabbitMqConnectionFactoryComponent conectionFactory;

    private final UsersService usersService;

    private AmqpTemplate amqpTemplate;

    private Gson gson;

    @Bean
    void initTemplate() {
        this.amqpTemplate = conectionFactory.rabbitTemplate();
    }

    private void sendMessage(UserQueueMessage user) {
        String json = gson.toJson(user);
        System.out.println(json);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKey(), json);
    }

    //TO DO: DELETE ########## DOAR PT. DEBUG
    @RabbitListener(queues = "${socialsync.rabbitmq.queue}")
    void receiveMessage(String msg) {
        System.out.println("RabbitMQ: " + gson.fromJson(msg, UserQueueMessage.class).toString());
    }
    //#########################

    @GetMapping
    public ResponseEntity<HashMap<Long, UserSelect>> fetchAllUsers() {
        return new ResponseEntity<>(usersService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> fetchUserById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(usersService.fetchUserById(id), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        usersService.addUser(user);
        sendMessage(new UserQueueMessage(QueueMessageType.CREATE, user));

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            usersService.updateUser(id, user);
            sendMessage(new UserQueueMessage(QueueMessageType.UPDATE, user));
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception ex) {
            sendMessage(new UserQueueMessage(QueueMessageType.CREATE, user));
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
        User dummyUser = new User();
        dummyUser.setId(id);
        sendMessage(new UserQueueMessage(QueueMessageType.DELETE, dummyUser));
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }


}
