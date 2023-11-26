package com.socialsync.querymicroservice.dto;

import com.redis.om.spring.annotations.Document;
import com.socialsync.querymicroservice.pojo.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.index.Indexed;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class CommentDTO {
    @Id
    @Indexed
    private String id;

    @Reference
    private UserDTO creator;

    @Indexed
    private String postId;
    private String content;
    private Integer score;
    private Long timestampCreated;
    private Long timestampUpdated;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPostId();
        this.content = comment.getContent();
        this.score = comment.getScore();
        this.timestampCreated = comment.getTimestampCreated();
        this.timestampUpdated = comment.getTimestampUpdated();
    }
}
