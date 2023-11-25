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
    private RabbitMqConnectionFactoryComponent connectionFactory;

    private final TopicService topicService;

    private AmqpTemplate amqpTemplate;

    private Gson gson;

    @Bean
    void initTemplate() { this.amqpTemplate = connectionFactory.rabbitTemplate(); }

    private void sendMessage(TopicQueueMessage topic) {
        String json = gson.toJson(topic);
        System.out.println(json);
        this.amqpTemplate.convertAndSend(connectionFactory.getExchange(),connectionFactory.getRoutingKey(), json);
    }

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
    public ResponseEntity<Topic> addPost(@RequestBody Topic post) {
        topicService.addTopic(post);
        sendMessage(new TopicQueueMessage(QueueMessageType.CREATE, post));

        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topic> updatePost(@PathVariable String id, @RequestBody Topic topic) {
        try {
            topicService.updateTopic(id, topic);
            sendMessage(new TopicQueueMessage(QueueMessageType.UPDATE, topic));
            return new ResponseEntity<>(topic, HttpStatus.OK);
        } catch (TopicNotFound e) {
            sendMessage(new TopicQueueMessage(QueueMessageType.CREATE, topic));
            return new ResponseEntity<>(topic, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id) {
        topicService.deleteTopic(id);
        Topic dummyTopic = new Topic();
        dummyTopic.setId(id);
        sendMessage(new TopicQueueMessage(QueueMessageType.DELETE, dummyTopic));
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}