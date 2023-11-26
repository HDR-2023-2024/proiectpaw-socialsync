package com.socialsync.topicsmicroservice.interfaces;

import com.socialsync.topicsmicroservice.pojo.Topic;
import com.socialsync.topicsmicroservice.util.exceptions.TopicNotFound;

import java.util.HashMap;

public interface TopicServiceMethods {
    HashMap<String, Topic> fetchAllTopics();
    Topic fetchTopicById(String id) throws TopicNotFound;
    void addTopic(Topic topic);
    void updateTopic(String id, Topic topic) throws TopicNotFound;
    void deleteTopic(String id) throws TopicNotFound;
}
