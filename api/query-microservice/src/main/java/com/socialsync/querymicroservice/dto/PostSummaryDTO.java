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
    private Integer score;

    public PostSummaryDTO(PostDocument post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.score = post.getScore();
    }
}