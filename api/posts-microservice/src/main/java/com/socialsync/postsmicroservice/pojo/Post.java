package com.socialsync.postsmicroservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document
public class Post {
    @Id
    private String id;
    private String creatorId;
    private String topicId;
    private String title;
    private String content;
    private Integer score = 0;
    private Long timestampCreated;
    private Long timestampUpdated;

    public Post(String creatorId, String topicId, String title, String content) {
        this.creatorId = creatorId;
        this.topicId = topicId;
        this.title = title;
        this.content = content;
        this.timestampCreated = Instant.now().getEpochSecond();
    }
}
