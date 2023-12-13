package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.documents.PostDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String id;
    private UserSummaryDTO creator;
    private String topicId;
    private String title;
    private String content;
    private Integer score;

    public PostDTO(PostDocument post) {
        this.id = post.getId();
        this.topicId = post.getTopicId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.score = post.getScore();
    }
}
