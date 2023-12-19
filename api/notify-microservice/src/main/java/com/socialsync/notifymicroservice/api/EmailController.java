package com.socialsync.notifymicroservice.api;

import com.google.gson.Gson;
import com.socialsync.notifymicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.notifymicroservice.pojo.Comment;
import com.socialsync.notifymicroservice.pojo.CommentQueueMessage;
import com.socialsync.notifymicroservice.pojo.Post;
import com.socialsync.notifymicroservice.pojo.PostQueueMessage;
import com.socialsync.notifymicroservice.service.CommentsService;
import com.socialsync.notifymicroservice.service.EmailService;
import com.socialsync.notifymicroservice.service.PostsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
@Slf4j
@Validated
public class EmailController {

    private CommentsService commentsService;
    private PostsService postsService;

    private RabbitMqConnectionFactoryComponent conectionFactory;

    private AmqpTemplate amqpTemplate;

    private Gson gson;

    private final EmailService emailService;

    @RabbitListener(queues = "${socialsync.rabbitmq.queue.comments}")
    void receiveCommentMessageComment(String msg) {
        CommentQueueMessage msgQ = gson.fromJson(msg, CommentQueueMessage.class);

        try {
            log.info(msgQ.getPost_id().toString());
            //postsService.savePost(msgQ);
        }
        catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    @RabbitListener(queues = "${socialsync.rabbitmq.queue.posts}")
    void receiveCommentMessagePost(String msg) {
        PostQueueMessage msgQ = gson.fromJson(msg, PostQueueMessage.class);

        try {
            log.info(msgQ.toString());
            postsService.savePost(msgQ);
        }
        catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    @Autowired
    public EmailController(EmailService emailService, RabbitMqConnectionFactoryComponent conectionFactory, Gson gson, PostsService postsService, CommentsService commentsService) {
        this.emailService = emailService;
        this.amqpTemplate = conectionFactory.rabbitTemplate();
        this.gson = gson;
        this.postsService = postsService;
        this.commentsService = commentsService;
    }


    @GetMapping("/send")
    public String sendTestEmail() {
        String to = "maria-gabriela.fodor@student.tuiasi.ro"; // Replace with the actual recipient email address
        String subject = "Test Email";
        String text = "This is a test email sent from Spring Boot.";

        try {
            emailService.sendEmail(to, subject, text);
            return "Email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email. Check the console for errors.";
        }
    }

    @GetMapping("post/{user_id}")
    public ResponseEntity<?> getNotificationsPosts(@PathVariable String user_id) {
        try {
            List<Post> notificationsPost = postsService.getAllNewPostNotifUser(user_id);

            // Add the "title" field to each post
            List<Map<String, Object>> postsWithTitle = new ArrayList<>();
            for (Post post : notificationsPost) {
                Map<String, Object> postWithTitle = new LinkedHashMap<>();
                postWithTitle.put("id", post.getId());
                postWithTitle.put("creatorId", post.getCreatorId());
                postWithTitle.put("topicId", post.getTopicId());
                postWithTitle.put("messageType", post.getMessageType());
                postWithTitle.put("postId", post.getPostId());
                postWithTitle.put("title", postsService.getPostName(post.getPostId())); // Replace "color" with "title"
                postWithTitle.put("seen", post.getSeen());
                postsWithTitle.add(postWithTitle);
            }

            return new ResponseEntity<>(postsWithTitle, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the exception appropriately
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("comment/{user_id}")
    public ResponseEntity<?> getNotificationsComments(@PathVariable String user_id){
        try {
            List<Comment> notificationsComm = commentsService.getAllNewCommentUser(user_id);
            return new ResponseEntity<>(notificationsComm, HttpStatus.OK);
        } catch (Exception e){

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
