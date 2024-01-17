package com.socialsync.commentsmicroservice.pojo;


import com.socialsync.commentsmicroservice.pojo.enums.QueueMessageType;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class CommentQueueMessage {
    private QueueMessageType type;
    private Comment comment;
}
