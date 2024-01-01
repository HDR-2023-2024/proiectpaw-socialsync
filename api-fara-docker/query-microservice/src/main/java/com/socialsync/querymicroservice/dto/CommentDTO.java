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
    private Long timestampCreated;

    public CommentDTO(CommentDocument commentDocument) {
        this.id = commentDocument.getId();
        this.creator = commentDocument.getCreator();
        this.content = commentDocument.getContent();
        this.timestampCreated = commentDocument.getTimestampCreated();
    }
}
