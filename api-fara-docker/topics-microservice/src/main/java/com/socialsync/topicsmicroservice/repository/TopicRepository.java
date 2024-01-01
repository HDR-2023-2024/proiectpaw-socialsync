package com.socialsync.topicsmicroservice.repository;

import com.socialsync.topicsmicroservice.pojo.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {
    Optional<List<Topic>> findTopicByName(String name);
}
