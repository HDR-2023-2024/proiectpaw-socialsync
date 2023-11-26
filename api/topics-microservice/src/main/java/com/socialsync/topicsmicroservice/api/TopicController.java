package com.socialsync.topicsmicroservice.api;

import com.google.gson.Gson;
import com.socialsync.topicsmicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.topicsmicroservice.pojo.Topic;
import com.socialsync.topicsmicroservice.pojo.TopicQueueMessage;
import com.socialsync.topicsmicroservice.pojo.enums.QueueMessageType;
import com.socialsync.topicsmicroservice.service.TopicService;
import com.socialsync.topicsmicroservice.util.exceptions.TopicNotFound;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
    public ResponseEntity<Topic> addPost(@RequestBody Topic topic) {
        topicService.addTopic(topic);
        return new ResponseEntity<>(topic, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topic> updatePost(@PathVariable String id, @RequestBody Topic topic) {
        try {
            topicService.updateTopic(id, topic);
            return new ResponseEntity<>(topic, HttpStatus.OK);
        } catch (TopicNotFound e) {
            return new ResponseEntity<>(topic, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id) {
        try {
            topicService.deleteTopic(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (TopicNotFound ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}