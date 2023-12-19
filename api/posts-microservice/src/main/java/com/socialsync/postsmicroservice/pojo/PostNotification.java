package com.socialsync.postsmicroservice.pojo;

import com.socialsync.postsmicroservice.pojo.enums.QueueMessageType;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class PostNotification {
    private String creator_id; // pentru a notifica creatorul
    private String topic_id; // pentru a notifica adminitstatorul de topic -- daca mai e ..
    private QueueMessageType messageType; // ce notificare este
    private String post_id; // pentru agregare in serviciul de notificare
    private String title; // pentru a se afisa direct
}
