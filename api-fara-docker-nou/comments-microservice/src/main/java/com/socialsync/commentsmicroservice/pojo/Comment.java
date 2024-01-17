package com.socialsync.commentsmicroservice.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class Comment {
    @Id
    private String id;
    private String creatorId;
    private String postId;
    private String content;
    private Integer score = 0;
    private Long timestampCreated;
    private Long timestampUpdated;

    public Comment(String creatorId, String postId, String content) {
        this.creatorId = creatorId;
        this.postId = postId;
        this.content = content;
    }
}
