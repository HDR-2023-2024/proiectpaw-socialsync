package com.socialsync.notifymicroservice.pojo;

import com.socialsync.notifymicroservice.pojo.enums.QueueMessageType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Document
@NoArgsConstructor
public class Post {
    @Id
    private String id;
    private String creatorId;
    private String topicId;
    private QueueMessageType messageType;
    private String postId;
    private String title;
}
