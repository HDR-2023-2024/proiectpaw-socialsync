package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.documents.PostDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSummaryDTO {
    private String id;
    private UserSummaryDTO creator;
    private String title;
    private String content;
    private Integer score;
    private Long timestampCreated;


    public PostSummaryDTO(PostDocument post) {
        this.id = post.getId();
        this.creator = post.getCreator();
        this.title = post.getTitle();
        this.content = post.getContent().substring(0, Math.min(post.getContent().length(), 500));
        this.score = post.getScore();
        this.timestampCreated = post.getTimestampCreated();
    }
}
