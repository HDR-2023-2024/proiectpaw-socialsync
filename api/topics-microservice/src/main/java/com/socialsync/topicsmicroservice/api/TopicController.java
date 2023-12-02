package com.socialsync.topicsmicroservice.api;

import com.google.gson.Gson;
import com.socialsync.topicsmicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.topicsmicroservice.pojo.AuthorizedInfo;
import com.socialsync.topicsmicroservice.pojo.Topic;
import com.socialsync.topicsmicroservice.pojo.TopicQueueMessage;
import com.socialsync.topicsmicroservice.pojo.enums.QueueMessageType;
import com.socialsync.topicsmicroservice.service.AuthorizationService;
import com.socialsync.topicsmicroservice.service.TopicService;
import com.socialsync.topicsmicroservice.util.exceptions.TopicNotFound;
import com.socialsync.topicsmicroservice.util.exceptions.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/topics")
@AllArgsConstructor
public class TopicController {
    private final TopicService topicService;
    private final AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<HashMap<String, Topic>> fetchAllPosts() {
        return new ResponseEntity<>(topicService.fetchAllTopics(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> fetchPostById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(topicService.fetchTopicById(id), HttpStatus.OK);
        } catch (TopicNotFound ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addPost(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Topic topic) {
        try {
            AuthorizedInfo authorizedInfo = authorizationService.authorized(authorizationHeader);
            topic.setCreatorId(authorizedInfo.getId());
            topic.setId(null);
            topicService.addTopic(topic);
            return new ResponseEntity<>(topic, HttpStatus.CREATED);
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String id, @RequestBody Topic topic) {
        AuthorizedInfo authorizedInfo = null;
        try {
             authorizedInfo = authorizationService.authorized(authorizationHeader);
            Topic topic1 = topicService.fetchTopicById(id);
            if (Objects.equals(topic1.getCreatorId(), authorizedInfo.getId())) {
                topicService.updateTopic(id, topic);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Este topicul altui utilizator!", HttpStatus.UNAUTHORIZED);
            }
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (TopicNotFound topicNotFound) {
            System.out.println("Topicul nu exista il creez");
            topic.setCreatorId(authorizedInfo.getId());
            topic.setId(null);
            topicService.addTopic(topic);
            return new ResponseEntity<>(topic, HttpStatus.CREATED);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String id) {
        try {
            AuthorizedInfo authorizedInfo = authorizationService.authorized(authorizationHeader);
            Topic topic = topicService.fetchTopicById(id);
            if (Objects.equals(authorizedInfo.getRole(), "admin") || Objects.equals(authorizedInfo.getId(), topic.getCreatorId())) {
                topicService.deleteTopic(id);
                return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Topicul nu poate fi sters decat de admin sau utilizatorul care la creat!", HttpStatus.UNAUTHORIZED);
            }
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (TopicNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}