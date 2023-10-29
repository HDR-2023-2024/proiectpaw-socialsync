package com.socialsync.postsmicroservice.api;

import com.google.gson.Gson;
import com.socialsync.postsmicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.postsmicroservice.pojo.Post;
import com.socialsync.postsmicroservice.pojo.PostQueueMessage;
import com.socialsync.postsmicroservice.pojo.enums.QueueMessageType;
import com.socialsync.postsmicroservice.service.PostsService;
import com.socialsync.postsmicroservice.util.exceptions.PostNotFound;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api/v1/posts")
@AllArgsConstructor
public class PostsController {

    private RabbitMqConnectionFactoryComponent conectionFactory;

    private final PostsService postsService;

    private AmqpTemplate amqpTemplate;

    private Gson gson;

    @Bean
    void initTemplate() {
        this.amqpTemplate = conectionFactory.rabbitTemplate();
    }

    private void sendMessage(PostQueueMessage post) {
        String json = gson.toJson(post);
        System.out.println(json);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKey(), json);
    }

    //TO DO: DELETE ########## DOAR PT. DEBUG
    @RabbitListener(queues = "${socialsync.rabbitmq.queue}")
    void receiveMessage(String msg) {
        System.out.println(gson.fromJson(msg, PostQueueMessage.class).toString());
    }
    //#########################

    @GetMapping
    public ResponseEntity<HashMap<String, Post>> fetchAllPosts() {
        return new ResponseEntity<>(postsService.fetchAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> fetchPostById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(postsService.fetchPostById(id), HttpStatus.OK);
        } catch (PostNotFound ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        postsService.addPost(post);
        sendMessage(new PostQueueMessage(QueueMessageType.CREATE, post));

        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post post) {
        try {
            postsService.updatePost(id, post);
            sendMessage(new PostQueueMessage(QueueMessageType.UPDATE, post));
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (PostNotFound ex) {
            sendMessage(new PostQueueMessage(QueueMessageType.CREATE, post));
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id) {
        postsService.deletePost(id);
        Post dummyPost = new Post();
        dummyPost.setId(id);
        sendMessage(new PostQueueMessage(QueueMessageType.DELETE, dummyPost));
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
