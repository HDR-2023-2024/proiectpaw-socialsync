package com.socialsync.notifymicroservice.api;

import com.google.gson.Gson;
import com.socialsync.notifymicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.notifymicroservice.dto.ContactForm;
import com.socialsync.notifymicroservice.dto.ReportDTO;
import com.socialsync.notifymicroservice.dto.ResetPasswordDto;
import com.socialsync.notifymicroservice.pojo.*;
import com.socialsync.notifymicroservice.service.CommentsService;
import com.socialsync.notifymicroservice.service.EmailService;
import com.socialsync.notifymicroservice.service.PostsService;
import com.socialsync.notifymicroservice.service.ReportService;
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

    private ReportService reportService;

    private RabbitMqConnectionFactoryComponent conectionFactory;

    private AmqpTemplate amqpTemplate;

    private Gson gson;

    private final EmailService emailService;


    @RabbitListener(queues = "${socialsync.rabbitmq.queue.comments}")
    void receiveQueueMessageComment(String msg) {
        CommentQueueMessage msgQ = gson.fromJson(msg, CommentQueueMessage.class);

        log.info("Processing comment {}", msgQ.toString());

        try {
            log.info(msgQ.getPost_id().toString());
            commentsService.saveComment(msgQ);
        }
        catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    @RabbitListener(queues = "${socialsync.rabbitmq.queue.posts}")
    void receiveQueueMessagePost(String msg) {
        PostQueueMessage msgQ = gson.fromJson(msg, PostQueueMessage.class);
        log.info("Processing post {}", msgQ.toString());

        try {
            log.info(msgQ.toString());
            postsService.savePost(msgQ);
        }
        catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }


    @Autowired
    public EmailController(EmailService emailService, RabbitMqConnectionFactoryComponent conectionFactory, Gson gson, PostsService postsService, CommentsService commentsService, ReportService reportService) {
        this.emailService = emailService;
        this.amqpTemplate = conectionFactory.rabbitTemplate();
        this.gson = gson;
        this.postsService = postsService;
        this.commentsService = commentsService;
        this.reportService = reportService;
    }

    @PostMapping("/send/{admin_mail}")
    public ResponseEntity<?> sendTestEmail(
            @PathVariable String admin_mail,
            @RequestBody ContactForm contactFormModel) {
        String to = admin_mail;
        String subject = contactFormModel.getSubject();
        String greetings = "Hi, Admin! \nAveti o notificare noua de la echipa Socialsync! \n";
        String text = greetings + "Dorim sa va informam ca utilizatorul cu numele" + contactFormModel.getNume() +
                " " + contactFormModel.getPrenume() +
                " va transmite urmatorul mesaj de contact: \n\n" + contactFormModel.getMessage() + "\n\n" +
                "Ii puteti raspunde la adresa de mail: " + contactFormModel.getEmail();
        log.info("Email text: {}", greetings + text);
        try {
            emailService.sendEmail(to, subject, text);
            return new ResponseEntity<>("Email send successfuly!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to send email. Check the console for errors.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("post/{user_id}")
    public ResponseEntity<?> getNotificationsPosts(@PathVariable String user_id) {
        try {
            List<Post> notificationsPost = postsService.getAllNewPostNotifUser(user_id);
            return new ResponseEntity<>(notificationsPost, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the exception appropriately
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("post/{notification_id}")
    public ResponseEntity<?> deletePostNotification(@PathVariable String notification_id){
        try {
            postsService.deletePostNotif(notification_id);
            return new ResponseEntity<>("Post notification deleted successfully!", HttpStatus.OK);
        } catch (Exception e){

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @DeleteMapping("comment/{notification_id}")
    public ResponseEntity<?> deleteCommentNotification(@PathVariable String notification_id){
        try {
            commentsService.deleteCommNotif(notification_id);
        } catch (Exception e){
            return new ResponseEntity<>("Comment notification deleted successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("reports")
    public ResponseEntity<?> getReports() {
        try {
            List<Report> reports = reportService.getAllReports();
            return new ResponseEntity<>(reports, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("reports/{report_id}")
    public ResponseEntity<?> deleteReport(@PathVariable String report_id) {
        try {
            reportService.deleteById(report_id);
            return new ResponseEntity<>("Report deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("reports")
    public ResponseEntity<?> saveReport(@RequestBody ReportDTO reportDTO) {
        try {
            if(reportService.saveReport(reportDTO)) {
                return new ResponseEntity<>("Report saved successfully!", HttpStatus.OK);
            }
            else
                // 422
                return  new ResponseEntity<>("Can not report a post for more than one time!", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/send-reset-password")
    public ResponseEntity<?> sendResetEmail(@RequestBody ResetPasswordDto resetPasswordDto) {
        try {
            emailService.sendEmail(resetPasswordDto.getDestination(), resetPasswordDto.getSubject(), resetPasswordDto.getMessage());
            return new ResponseEntity<>("Email send successfuly!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to send email. Check the console for errors.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
