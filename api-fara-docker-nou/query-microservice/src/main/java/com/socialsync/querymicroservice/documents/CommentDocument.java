package com.socialsync.querymicroservice.documents;

import com.redis.om.spring.annotations.Document;
import com.socialsync.querymicroservice.dto.UserSummaryDTO;
import com.socialsync.querymicroservice.pojo.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class CommentDocument {
    @Id
    private String id;
    @Indexed
    private UserSummaryDTO creator;

    @Indexed
    private String postId;
    private String content;
    private Long timestampCreated;
    private Long timestampUpdated;

    public CommentDocument(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPostId();
        this.content = comment.getContent();
        this.timestampCreated = comment.getTimestampCreated();
        this.timestampUpdated = comment.getTimestampUpdated();
    }
}
