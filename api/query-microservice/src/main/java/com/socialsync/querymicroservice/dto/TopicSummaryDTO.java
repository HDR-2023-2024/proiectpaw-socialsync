package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.documents.TopicDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicSummaryDTO {
    private String id;
    private UserSummaryDTO creator;
    private String name;
    private String description;

    public TopicSummaryDTO(TopicDocument topic) {
        this.id = topic.getId();
        this.creator = topic.getCreator();
        this.name = topic.getName();
        this.description = topic.getDescription();
    }
}
