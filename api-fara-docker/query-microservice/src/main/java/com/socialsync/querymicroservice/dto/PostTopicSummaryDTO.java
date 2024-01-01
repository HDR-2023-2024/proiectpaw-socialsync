package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.documents.TopicDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostTopicSummaryDTO {
    private String id;
    private String title;
    private String photoId;

    public PostTopicSummaryDTO(TopicDocument topicDocument) {
        this.id = topicDocument.getId();
        this.title = topicDocument.getName();
        this.photoId = topicDocument.getPhotoId();
    }
}
