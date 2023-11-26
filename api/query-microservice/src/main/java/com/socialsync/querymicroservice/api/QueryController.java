package com.socialsync.querymicroservice.api;

import com.google.gson.Gson;
import com.socialsync.querymicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.querymicroservice.pojo.CommentQueueMessage;
import com.socialsync.querymicroservice.pojo.PostQueueMessage;
import com.socialsync.querymicroservice.pojo.TopicQueueMessage;
import com.socialsync.querymicroservice.pojo.UserQueueMessage;
import com.socialsync.querymicroservice.service.QueryQueryService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/query")
@AllArgsConstructor
@Slf4j
@Validated
public class QueryController {
    private RabbitMqConnectionFactoryComponent conectionFactory;

    private AmqpTemplate amqpTemplate;

    private Gson gson;

    private QueryQueryService queryService;

    @Bean
    void initTemplate() {
        this.amqpTemplate = conectionFactory.rabbitTemplate();
    }
    @RabbitListener(queues = "${socialsync.rabbitmq.queue1}")
    void receiveCommentMessage(String msg) {
        CommentQueueMessage msgQ = gson.fromJson(msg, CommentQueueMessage.class);
        try {
            log.info(msgQ.getComment().toString());
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
            log.info(msgQ.getPost().toString());
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
            log.info(msgQ.getUser().toString());
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
            log.info(msgQ.getTopic().toString());
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
                .body(queryService.fetchAllComments(parsePage(page)));
    }


    @GetMapping("/post/{id}/comments")
    public ResponseEntity<?> fetchCommentsForPost(@NotNull @PathVariable String id, @NotNull @RequestParam Integer page) {
        return ResponseEntity
                .ok()
                .body(queryService.fetchAllCommentsByPostId(parsePage(page), id));
    }

    @GetMapping("/posts")
    public ResponseEntity<?> fetchPosts(@NotNull @RequestParam Integer page) {
        return ResponseEntity
                .ok()
                .body(queryService.fetchAllPosts(parsePage(page)));
    }

    @GetMapping("/topic/{id}/posts")
    public ResponseEntity<?> fetchPostsByTopicId(@NotNull @PathVariable String id, @NotNull @RequestParam Integer page) {
        return ResponseEntity
                .ok()
                .body(queryService.fetchAllPostByTopicId(parsePage(page), id));
    }

    @GetMapping("/topics")
    public ResponseEntity<?> fetchTopics(@NotNull @RequestParam Integer page, @RequestParam Optional<String> name) {
        return name.map(s -> ResponseEntity
                    .ok()
                    .body(queryService.fetchAllTopicsByName(s, page)))
                .orElseGet(() -> ResponseEntity
                    .ok()
                    .body(queryService.fetchAllTopics(parsePage(page))));
    }

    @GetMapping("/users")
    public ResponseEntity<?> fetchUsers(@NotNull @RequestParam Integer page, @RequestParam Optional<String> username) {
        return username.map(s -> ResponseEntity
                    .ok()
                    .body(queryService.fetchAllUsersByUsername(s, parsePage(page))))
                .orElseGet(() -> ResponseEntity
                    .ok()
                    .body(queryService.fetchAllUsers(parsePage(page))));
    }

    @GetMapping("/user/{id}/comments")
    public ResponseEntity<?> fetchUserComments(@NotNull @RequestParam Integer page, @PathVariable String id) {
        return ResponseEntity
                .ok()
                .body(queryService.fetchAllCommentsByUserId(parsePage(page), id));
    }

    @GetMapping("/user/{id}/posts")
    public ResponseEntity<?> fetchUserPosts(@NotNull @RequestParam Integer page, @PathVariable String id) {
        return ResponseEntity
                .ok()
                .body(queryService.fetchAllPostByUserId(parsePage(page), id));
    }

    private static Integer parsePage(Integer page) {
        return page == 0 ? page : page < 0 ? 0 : page - 1;
    }
}
