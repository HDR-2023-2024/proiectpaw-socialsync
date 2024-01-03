package com.socialsync.commentsmicroservice.service;

import com.google.gson.Gson;
import com.socialsync.commentsmicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.commentsmicroservice.interfaces.CommentsServiceMethods;
import com.socialsync.commentsmicroservice.pojo.*;
import com.socialsync.commentsmicroservice.pojo.enums.QueueMessageType;
import com.socialsync.commentsmicroservice.repository.CommentRepository;
import com.socialsync.commentsmicroservice.repository.PostIdRepository;
import com.socialsync.commentsmicroservice.repository.UserIdRepository;
import com.socialsync.commentsmicroservice.util.exceptions.CommentNotFound;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
@EnableScheduling
public class CommentsService implements CommentsServiceMethods {

    private CommentRepository repository;
    private PostIdRepository postIdRepository;
    private UserIdRepository userIdRepository;

    private RabbitMqConnectionFactoryComponent conectionFactory;

    private AmqpTemplate amqpTemplate;

    private Gson gson;

    @Bean
    void initTemplate() {
        this.amqpTemplate = conectionFactory.rabbitTemplate();
    }

    @RabbitListener(queues = "${socialsync.rabbitmq.queue.postId}")
    void postIdListener(String msg) {
        try {
            if (msg.startsWith("DEL")) {
                String id = msg.split("#")[1];
                log.info("Deleting post " + id);
                postIdRepository.deleteById(id);
            }
            else {
                log.info("New post " + msg);
                postIdRepository.save(new Post(msg));
                populateDb();
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

    @RabbitListener(queues = "${socialsync.rabbitmq.queue.userId}")
    void userIdListener(String msg) {
        try {
            if (msg.startsWith("DEL")) {
                String id = msg.split("#")[1];
                log.info("Deleting user " + id);
                userIdRepository.deleteById(id);
            }
            else {
                log.info("New user " + msg);
                userIdRepository.save(new User(msg));
                populateDb();
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

    private void sendMessage(CommentQueueMessage comment) {
        String json = gson.toJson(comment);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKeyComments(), json);
    }

    private void sendMessageNotification(CommentNotification comment) {
        String json = gson.toJson(comment);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKeyNotify(), json);
    }

    @Bean
    void deleteEverything() {
        postIdRepository.deleteAll();
        userIdRepository.deleteAll();
        repository.findAll().forEach(x -> {
            try {
                deleteComment(x.getId());
            } catch (CommentNotFound e) {
                throw new RuntimeException(e);
            }
        });
    }

    void populateDb() {
        Random random = new Random();
        List<String> reactie = List.of("NASPA", "MEH", "BUNA");


        List<Post> postsId = postIdRepository.findAll().stream().toList();
        List<User> usersId = userIdRepository.findAll().stream().toList();

        if (postsId.size() > 1 && usersId.size() > 1) {
            Comment comment = new Comment(usersId.get(random.nextInt(usersId.size())).getId(), postsId.get(random.nextInt(postsId.size())).getId(), "POSTARE " + reactie.get(new Random().nextInt(reactie.size())) + " UNGA BUNGA!");
            addComment(comment);
            log.info("Created " + comment);
        }
    }

  /*  @Bean
    @Scheduled(fixedDelay = 5000L)
    void newRandomComment() {
        List<String> reactie = List.of("NASPA", "MEH", "BUNA");
        log.info("We have " + repository.findAll().size() + " comments");

//        if (repository.findAll().size() > 200)
//            deleteEverything();

        Comment comment = new Comment("-1", "-1", "POSTARE " + reactie.get(new Random().nextInt(reactie.size())) + " UNGA BUNGA!");
        addComment(comment);
    }
*/
    @Override
    public HashMap<String, Comment> fetchAllComments() {
        HashMap<String, Comment> lista = new HashMap<>();

        List<Comment> comments = repository.findAll();

        for (Comment comment : comments)
            lista.put(comment.getId(), comment);

        return lista;
    }

    @Override
    public Comment fetchCommentById(String id) throws CommentNotFound {
        return repository.findById(id).orElseThrow(() -> new CommentNotFound("Not found: " + id));
    }

    @Override
    public void addComment(Comment comment) {
        comment.setTimestampCreated(Instant.now().getEpochSecond());
        repository.insert(comment);
        sendMessage(new CommentQueueMessage(QueueMessageType.CREATE, comment));
        sendMessageNotification(new CommentNotification(comment.getCreatorId(), comment.getPostId(), comment.getContent().substring(0, Math.min(comment.getContent().length(), 50))));
    }

    @Override
    public void updateComment(String id, Comment comment) throws CommentNotFound {
        repository.findById(id).map(elem -> {
            elem.setContent(comment.getContent());
            elem.setCreatorId(comment.getCreatorId());
            elem.setPostId(comment.getPostId());
            elem.setTimestampUpdated(Instant.now().getEpochSecond());
            repository.save(elem);
            sendMessage(new CommentQueueMessage(QueueMessageType.UPDATE, comment));
            return elem;
        }).orElseThrow(() -> {
            comment.setTimestampCreated(Instant.now().getEpochSecond());
            repository.insert(comment);
            sendMessage(new CommentQueueMessage(QueueMessageType.CREATE, comment));
            return new CommentNotFound("Comment not found. Created one instead.");
        });
    }

    @Override
    public void deleteComment(String id) throws CommentNotFound {
        Optional<Comment> comment = repository.findById(id);

        if (comment.isPresent()) {
            repository.deleteById(id);
            sendMessage(new CommentQueueMessage(QueueMessageType.DELETE, comment.get()));
        }
        else
            throw new CommentNotFound("Comment not found");
    }
}
