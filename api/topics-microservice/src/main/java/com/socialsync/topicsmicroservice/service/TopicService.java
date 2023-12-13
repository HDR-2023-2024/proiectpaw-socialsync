package com.socialsync.topicsmicroservice.service;

import com.google.gson.Gson;
import com.socialsync.topicsmicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.topicsmicroservice.interfaces.TopicServiceMethods;
import com.socialsync.topicsmicroservice.pojo.Topic;
import com.socialsync.topicsmicroservice.pojo.TopicQueueMessage;
import com.socialsync.topicsmicroservice.pojo.enums.QueueMessageType;
import com.socialsync.topicsmicroservice.repository.TopicRepository;
import com.socialsync.topicsmicroservice.util.exceptions.TopicNotFound;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
@EnableScheduling
public class TopicService implements TopicServiceMethods {

    private TopicRepository repository;

    private RabbitMqConnectionFactoryComponent conectionFactory;

    private AmqpTemplate amqpTemplate;

    private Gson gson;

    static List<Topic> listaTopicuri = List.of(
            new Topic("Dezbate", "O dezbatere captivantă asupra misterele orașului ascuns", false, "-1"),
            new Topic("Explorare", "Explorarea fascinantă a fenomenului luminilor nocturne", false, "-1"),
            new Topic("Analiza", "Analiza profundă a călătoriei prin portalele universului", true, "-1"),
            new Topic("Discuție", "O discuție deschisă despre cărțile trecutului și descoperirea misterelelor", false, "-1"),
            new Topic("Cercetare", "Cercetarea nelimitată în căutarea aventurii", false, "-1"),
            new Topic("Opinie", "O discuție liberă despre secretele stelelor și galaxiilor", false, "-1"),
            new Topic("Sondaj", "Un sondaj privind sensul vieții și existența", false, "-1"),
            new Topic("Dezbatere", "O dezbatere pasionantă despre locurile magice și neexplorate", false, "-1"),
            new Topic("Analiză", "Analiza realităților ascunse și mistice", false, "-1"),
            new Topic("Cercetare", "Călătorie în lumi fantastice și povestiri cosmice", false, "-1")
    );

    @Bean
    void initTemplate() {
        this.amqpTemplate = conectionFactory.rabbitTemplate();
    }

    private void sendMessage(TopicQueueMessage topic) {
        String json = gson.toJson(topic);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKey(), json);
    }

    void deleteEverything() {
        repository.findAll().forEach(x -> {
            try {
                deleteTopic(x.getId());
            } catch (TopicNotFound e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Bean
    void populateDb() {
        deleteEverything();
        listaTopicuri.forEach(this::addTopic);
    }

    @Bean
    @Scheduled(fixedDelay = 30000L)
    void newRandomTopic() {
        log.info("We have " + repository.findAll().size() + " topic");

//        if (repository.findAll().size() > 50)
//            deleteEverything();

        Topic topic = listaTopicuri.get(new Random().nextInt(listaTopicuri.size()));
        Topic newTopic = new Topic();
        newTopic.setName(topic.getName() + new Random().nextInt(10000));
        newTopic.setDescription(topic.getDescription() + new Random().nextInt(10000));
        newTopic.setAgeRestriction(false);
        newTopic.setCreatorId("-1");
        addTopic(newTopic);
    }

    @Override
    public HashMap<String, Topic> fetchAllTopics() {
        HashMap<String, Topic> all_topics = new HashMap<>();

        List<Topic> topics = repository.findAll();

        for (Topic topic : topics)
            all_topics.put(topic.getId(), topic);
        return all_topics;
    }

    @Override
    public Topic fetchTopicById(String id) throws TopicNotFound {
        return repository.findById(id).orElseThrow(() -> new TopicNotFound("Not fount " + id));
    }

    @Override
    public void addTopic(Topic topic) {
        topic.setTimestampCreated(Instant.now().getEpochSecond());
        repository.save(topic);
        sendMessage(new TopicQueueMessage(QueueMessageType.CREATE, topic));
    }

    @Override
    public void updateTopic(String id, Topic topic) throws TopicNotFound {
        repository.findById(id).map(elem -> {
            elem.setName(topic.getName());
            elem.setDescription(topic.getDescription());
            elem.setAgeRestriction(topic.getAgeRestriction());
            elem.setPhotoId(topic.getPhotoId());
            elem.setCreatorId(topic.getCreatorId());
            elem.setTimestampUpdated(Instant.now().getEpochSecond());
            repository.save(elem);
            sendMessage(new TopicQueueMessage(QueueMessageType.UPDATE, topic));
            return elem;
        }).orElseThrow(() -> {
            topic.setTimestampCreated(Instant.now().getEpochSecond());
            topic.setId(null);
            repository.insert(topic);
            sendMessage(new TopicQueueMessage(QueueMessageType.CREATE, topic));
            return new TopicNotFound("Topic not found. Created one instead.");
        });
    }

    @Override
    public void deleteTopic(String id) throws TopicNotFound {
        Optional<Topic> topic = repository.findById(id);

        if (topic.isPresent()) {
            repository.deleteById(id);
            sendMessage(new TopicQueueMessage(QueueMessageType.DELETE, topic.get()));
        }
        else
            throw new TopicNotFound("Topic not found.");
    }
}
