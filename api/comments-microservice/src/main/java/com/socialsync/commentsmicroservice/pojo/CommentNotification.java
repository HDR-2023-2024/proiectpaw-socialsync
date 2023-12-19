package com.socialsync.commentsmicroservice.pojo;

import com.socialsync.commentsmicroservice.pojo.enums.QueueMessageType;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class CommentNotification {
    private String user_id;
    private String post_id;
    private String comm;
}
