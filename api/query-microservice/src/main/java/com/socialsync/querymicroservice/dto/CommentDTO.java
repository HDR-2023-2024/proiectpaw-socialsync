package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.documents.CommentDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private String id;
    private UserSummaryDTO creator;
    private String content;
    private Integer score;
    private Long timestampCreated;

    public CommentDTO(CommentDocument commentDocument) {
        this.id = commentDocument.getId();
        this.content = commentDocument.getContent();
        this.score = commentDocument.getScore();
        this.timestampCreated = commentDocument.getTimestampCreated();
    }
}
