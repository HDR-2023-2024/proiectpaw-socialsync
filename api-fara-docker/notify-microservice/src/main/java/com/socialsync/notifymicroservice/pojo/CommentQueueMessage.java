package com.socialsync.notifymicroservice.pojo;


import com.socialsync.notifymicroservice.pojo.enums.QueueMessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class CommentQueueMessage {
    private String user_id;
    private String post_id;
    private String comm;
}
