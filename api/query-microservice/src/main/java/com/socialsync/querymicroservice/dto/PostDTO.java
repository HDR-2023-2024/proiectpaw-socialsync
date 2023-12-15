package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.documents.PostDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String id;
    private UserSummaryDTO creator;
    private String topicId;
    private String title;
    private String content;
    private List<CommentDTO> comments = new ArrayList<>();
    private Integer score;
    private Long timestampCreated;


    public PostDTO(PostDocument post, List<CommentDTO> comments) {
        this.id = post.getId();
        this.topicId = post.getTopicId();
        this.creator = post.getCreator();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.score = post.getScore();
        this.comments.addAll(comments);
        this.timestampCreated = post.getTimestampCreated();
    }
}
