package com.socialsync.postsmicroservice.pojo;

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
    private String title;
    private String content;
    private Set<String> upvotes = new HashSet<>();
    private Set<String> downvotes = new HashSet<>();
    private Set<String> photos = new HashSet<>();
    private Integer score = 0;
    private Long timestampCreated;
    private Long timestampUpdated;

    public Post(String creatorId, String topicId, String title, String content) {
        this.creatorId = creatorId;
        this.topicId = topicId;
        this.title = title;
        this.content = content;
    }

    public Post(String id, String creatorId, String topicId) {
        this.id = id;
        this.creatorId = creatorId;
        this.topicId = topicId;
    }
}
