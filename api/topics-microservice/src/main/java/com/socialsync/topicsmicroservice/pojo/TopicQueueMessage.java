package com.socialsync.topicsmicroservice.pojo;

import com.socialsync.topicsmicroservice.pojo.enums.QueueMessageType;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class TopicQueueMessage {
    private QueueMessageType type;
    private Topic topic;
}
