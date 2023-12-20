package com.socialsync.querymicroservice.api;

import com.google.gson.Gson;
import com.socialsync.querymicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.querymicroservice.documents.PostDocument;
import com.socialsync.querymicroservice.documents.TopicDocument;
import com.socialsync.querymicroservice.documents.UserDocument;
import com.socialsync.querymicroservice.dto.PostDTO;
import com.socialsync.querymicroservice.dto.PostTopicSummaryDTO;
import com.socialsync.querymicroservice.dto.TopicDTO;
import com.socialsync.querymicroservice.dto.UserDTO;
import com.socialsync.querymicroservice.pojo.CommentQueueMessage;
import com.socialsync.querymicroservice.pojo.PostQueueMessage;
import com.socialsync.querymicroservice.pojo.TopicQueueMessage;
import com.socialsync.querymicroservice.pojo.UserQueueMessage;
import com.socialsync.querymicroservice.service.QueryService;
import com.socialsync.querymicroservice.util.exceptions.TopicException;
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

    private QueryService queryService;

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
                .ok(queryService.fetchAllComments(parsePage(page)));
    }


    @GetMapping("/posts")
    public ResponseEntity<?> fetchPosts(@NotNull @RequestParam Integer page, Optional<String> query, @RequestHeader("X-User-Id") Optional<String> userId) {
        return query.map(s -> ResponseEntity
                    .ok(queryService.searchPostByTitle(s, parsePage(page), userId)))
                .orElseGet(() -> ResponseEntity
                    .ok(queryService.fetchAllPosts(parsePage(page), userId)));

    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> fetchPost(@PathVariable String id, @NotNull @RequestParam Integer page, @RequestHeader("X-User-Id") Optional<String> userId) {
        Optional<PostDocument> post = queryService.fetchPostById(id);

        if (post.isPresent()) {
            PostDTO postResponse = new PostDTO(
                    post.get(),
                    queryService.fetchPostComments(post.get().getId(), parsePage(page)),
                    new PostTopicSummaryDTO(queryService.fetchTopic(post.get().getTopicId()).orElseThrow(() -> new TopicException("Topic not found for post " + post.get().getId()))));

            if (userId.isPresent())
            {
                if (post.get().getDownvotes().contains(userId.get()))
                    postResponse.setDislikedByUser(true);
                else if (post.get().getUpvotes().contains(userId.get()))
                    postResponse.setLikedByUser(true);
            }

            return ResponseEntity
                    .ok(postResponse);
        }

        return ResponseEntity
                .ok("");
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<?> fetchPostComments(@PathVariable String id, @NotNull @RequestParam Integer page) {
        log.info(id);
        return ResponseEntity
                .ok(queryService.fetchPostComments(id, parsePage(page)));
    }

    @GetMapping("/topics")
    public ResponseEntity<?> fetchTopics(@NotNull @RequestParam Integer page, Optional<String> query) {
        return query.map(s -> ResponseEntity
                    .ok(queryService.searchTopicsByName(s, parsePage(page))))
                .orElseGet(() -> ResponseEntity
                    .ok(queryService.fetchAllTopics(parsePage(page))));
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<?> fetchTopic(@PathVariable String id, @RequestHeader("X-User-Id") Optional<String> userId) {
        Optional<TopicDocument> topic = queryService.fetchTopic(id);

        return ResponseEntity
                .ok(topic.isPresent() ? new TopicDTO(topic.get(), queryService.fetchTopicPosts(id, 0, userId)) : "shit");
    }

    @GetMapping("/topics/{id}/posts")
    public ResponseEntity<?> fetchTopicPosts(@PathVariable String id, @NotNull @RequestParam Integer page, @RequestHeader("X-User-Id") Optional<String> userId) {
        return ResponseEntity
                .ok(queryService.fetchTopicPosts(id, parsePage(page), userId));
    }

    @GetMapping("/users")
    public ResponseEntity<?> fetchUsers(@NotNull @RequestParam Integer page, Optional<String> query) {
        return query.map(s -> ResponseEntity
                    .ok(queryService.searchUsersByUsername(s, parsePage(page))))
                .orElseGet(() -> ResponseEntity
                    .ok(queryService.fetchAllUsers(parsePage(page))));

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> fetchUser(@PathVariable String id) {
        Optional<UserDocument> user = queryService.fetchUser(id);

        return ResponseEntity
                .ok(user.isPresent() ? new UserDTO(user.get()) : "");
    }

    @GetMapping("/users/{id}/topics")
    public ResponseEntity<?> fetchUserTopics(@PathVariable String id, @NotNull @RequestParam Integer page) {
        return ResponseEntity.ok(queryService.fetchTopicsByCreatorId(id, parsePage(page)));
    }

    @GetMapping("/users/{id}/posts")
    public ResponseEntity<?> fetchUserPosts(@PathVariable String id, @NotNull @RequestParam Integer page) {
        return ResponseEntity.ok(queryService.fetchPostsByCreatorId(id, parsePage(page)));
    }

    @GetMapping("/users/{id}/comments")
    public ResponseEntity<?> fetchUserComments(@PathVariable String id, @NotNull @RequestParam Integer page) {
        return ResponseEntity.ok(queryService.fetchCommentsByCreatorId(id, parsePage(page)));
    }

    private static Integer parsePage(Integer page) {
        return page == 0 ? page : page < 0 ? 0 : page ;
    }
}
