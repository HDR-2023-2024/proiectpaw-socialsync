package com.socialsync.querymicroservice.pojo;

import com.socialsync.querymicroservice.pojo.enums.QueueMessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class UserQueueMessage {
    private QueueMessageType type;
    private UserSelect user;
}
