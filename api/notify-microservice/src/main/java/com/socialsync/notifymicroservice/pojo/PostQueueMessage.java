package com.socialsync.notifymicroservice.pojo;


import com.socialsync.notifymicroservice.pojo.enums.QueueMessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class PostQueueMessage {
    private String creator_id; // pentru a notifica creatorul
    private String topic_id; // pentru a notifica adminitstatorul de topic
    private QueueMessageType messageType;
    private String post_id;
    private String title;
}
