package com.socialsync.commentsmicroservice.api;

import com.google.gson.Gson;
import com.socialsync.commentsmicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.commentsmicroservice.pojo.Comment;
import com.socialsync.commentsmicroservice.pojo.CommentQueueMessage;
import com.socialsync.commentsmicroservice.pojo.enums.QueueMessageType;
import com.socialsync.commentsmicroservice.service.CommentsService;
import com.socialsync.commentsmicroservice.util.exceptions.CommentNotFound;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api/v1/comments")
@AllArgsConstructor
public class CommentsController {

    private RabbitMqConnectionFactoryComponent conectionFactory;

    private final CommentsService commentsService;

    private AmqpTemplate amqpTemplate;

    private Gson gson;

    @Bean
    void initTemplate() {
        this.amqpTemplate = conectionFactory.rabbitTemplate();
    }

    private void sendMessage(CommentQueueMessage comment) {
        String json = gson.toJson(comment);
        System.out.println(json);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKey(), json);
    }

    @GetMapping
    public ResponseEntity<HashMap<String, Comment>> fetchAllComments() {
        return new ResponseEntity<>(commentsService.fetchAllComments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> fetchCommentById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(commentsService.fetchCommentById(id), HttpStatus.OK);
        } catch (CommentNotFound ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        commentsService.addComment(comment);
        sendMessage(new CommentQueueMessage(QueueMessageType.CREATE, comment));

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable String id, @RequestBody Comment comment) {
        try {
            commentsService.updateComment(id, comment);
            sendMessage(new CommentQueueMessage(QueueMessageType.UPDATE, comment));
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (CommentNotFound ex) {
            sendMessage(new CommentQueueMessage(QueueMessageType.CREATE, comment));
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable String id) {
        commentsService.deleteComment(id);
        Comment dummyComment = new Comment();
        dummyComment.setId(id);
        sendMessage(new CommentQueueMessage(QueueMessageType.DELETE, dummyComment));
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
