package com.socialsync.topicsmicroservice.api;

import com.socialsync.topicsmicroservice.pojo.AuthorizedInfo;
import com.socialsync.topicsmicroservice.pojo.Topic;
import com.socialsync.topicsmicroservice.service.TopicService;
import com.socialsync.topicsmicroservice.util.exceptions.TopicNotFound;
import com.socialsync.topicsmicroservice.util.exceptions.UnauthorizedException;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<?> addPost(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole, @RequestBody Topic topic) {
            topic.setCreatorId(userId);
            topic.setId(null);
            topicService.addTopic(topic);
            return new ResponseEntity<>(topic, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole, @PathVariable String id, @RequestBody Topic topic) {
        try {
            Topic topic1 = topicService.fetchTopicById(id);
            if (Objects.equals(topic1.getCreatorId(), userId)) {
                topicService.updateTopic(id, topic);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Este topicul altui utilizator!", HttpStatus.UNAUTHORIZED);
            }
        } catch (TopicNotFound topicNotFound) {
            System.out.println("Topicul nu exista il creez");
            topic.setCreatorId(userId);
            topic.setId(null);
            topicService.addTopic(topic);
            return new ResponseEntity<>(topic, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}/join")
    public ResponseEntity<?> joinTopic(@RequestHeader("X-User-Id") String userId, @PathVariable String id) {
        try {

            topicService.joinTopic(id, userId);
            return ResponseEntity
                    .noContent()
                    .build();
        } catch (TopicNotFound ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole, @PathVariable String id) {
        try {
            Topic topic = topicService.fetchTopicById(id);
            if (Objects.equals(userRole, "admin") || Objects.equals(userId, topic.getCreatorId())) {
                topicService.deleteTopic(id);
                return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Topicul nu poate fi sters decat de admin sau utilizatorul care la creat!", HttpStatus.UNAUTHORIZED);
            }
        } catch (TopicNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}