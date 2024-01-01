package com.socialsync.postsmicroservice.pojo;


import com.socialsync.postsmicroservice.pojo.enums.QueueMessageType;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class PostQueueMessage {
    private QueueMessageType type;
    private Post post;
}
