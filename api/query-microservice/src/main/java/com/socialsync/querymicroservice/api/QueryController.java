package com.socialsync.querymicroservice.api;

import com.google.gson.Gson;
import com.socialsync.querymicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.querymicroservice.pojo.CommentQueueMessage;
import com.socialsync.querymicroservice.pojo.PostQueueMessage;
import com.socialsync.querymicroservice.pojo.TopicQueueMessage;
import com.socialsync.querymicroservice.pojo.UserQueueMessage;
import com.socialsync.querymicroservice.service.QueryService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/query")
@AllArgsConstructor
@Slf4j
@Validated
public class QueryController {
    private RabbitMqConnectionFactoryComponent conectionFactory;

    private AmqpTemplate amqpTemplate;

    private Gson gson;

    private RedisTemplate<String, String> template;

    private QueryService queryService;

    @Bean
    void initTemplate() {
        this.amqpTemplate = conectionFactory.rabbitTemplate();
    }
    @RabbitListener(queues = "${socialsync.rabbitmq.queue1}")
    void receiveCommentMessage(String msg) {
        CommentQueueMessage msgQ = gson.fromJson(msg, CommentQueueMessage.class);
        try {
            queryService.handleComment(msgQ);
        }
        catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    @RabbitListener(queues = "${socialsync.rabbitmq.queue2}")
    void receivePostMessage(String msg) {
        PostQueueMessage msgQ = gson.fromJson(msg, PostQueueMessage.class);

        try {
            queryService.handlePost(msgQ);
        }
        catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    @RabbitListener(queues = "${socialsync.rabbitmq.queue3}")
    void receiveUserMessage(String msg) {
        UserQueueMessage msgQ =  gson.fromJson(msg, UserQueueMessage.class);
        try {
            queryService.handleUser(msgQ);
        }
        catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    @RabbitListener(queues = "${socialsync.rabbitmq.queue4}")
    void receiveTopicMessage(String msg) {
        TopicQueueMessage msgQ = gson.fromJson(msg, TopicQueueMessage.class);
        try {
            queryService.handleTopic(msgQ);
        }
        catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    @GetMapping("/comments")
    public ResponseEntity<?> fetchComments(@NotNull @RequestParam Integer page) {
        return ResponseEntity
                .ok()
                .body(queryService.fetchAllComments(page == 0 ? page : page < 0 ? 0 : page - 1));
    }

    @GetMapping("/posts")
    public ResponseEntity<?> fetchPosts(@NotNull @RequestParam Integer page) {
        return ResponseEntity
                .ok()
                .body(queryService.fetchAllPosts(page == 0 ? page : page < 0 ? 0 : page - 1));
    }

    @GetMapping("/users")
    public ResponseEntity<?> fetchUsers(@NotNull @RequestParam Integer page) {
        return ResponseEntity
                .ok()
                .body(queryService.fetchAllUsers(page == 0 ? page : page < 0 ? 0 : page - 1));
    }

    @GetMapping("/topics")
    public ResponseEntity<?> fetchTopics(@NotNull @RequestParam Integer page) {
        return ResponseEntity
                .ok()
                .body(queryService.fetchAllTopics(page == 0 ? page : page < 0 ? 0 : page - 1));
    }
}
