package com.socialsync.topicsmicroservice.service;

import com.socialsync.topicsmicroservice.interfaces.TopicServiceMethods;
import com.socialsync.topicsmicroservice.pojo.Topic;
import com.socialsync.topicsmicroservice.repository.TopicRepository;
import com.socialsync.topicsmicroservice.util.exceptions.TopicNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class TopicService implements TopicServiceMethods {

    private TopicRepository repository;
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
    }

    @Override
    public void updateTopic(String id, Topic topic) throws TopicNotFound {
        repository.findById(id).map(elem -> {
            elem.setName(topic.getName());
            elem.setDescription(topic.getDescription());
            elem.setAgeRestriction(topic.getAgeRestriction());
            elem.setPhotoId(topic.getPhotoId());
            elem.setMemberIds(topic.getMemberIds());
            elem.setPostIds(topic.getPostIds());
            elem.setCreatorId(topic.getCreatorId());
            elem.setTimestampUpdated(Instant.now().getEpochSecond());
            repository.save(elem);
            return elem;
        }).orElseThrow(() -> {
            topic.setTimestampCreated(Instant.now().getEpochSecond());
            repository.insert(topic);
            return new TopicNotFound("Topic not found. Created one instead.");
        });
    }

    @Override
    public void deleteTopic(String id) {
        repository.deleteById(id);
    }
}
