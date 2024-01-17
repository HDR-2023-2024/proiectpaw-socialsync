package com.socialsync.notifymicroservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// inforamtii necesare agregarii pe notificarea comentariului
@Data
@Document
@AllArgsConstructor
public class PersistentPost {
    @Id
    private String postId;
    private String title;
    private String creatorId;
}