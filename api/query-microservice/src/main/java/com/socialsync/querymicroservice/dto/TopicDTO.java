package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.documents.TopicDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDTO {
    private String id;
    private UserSummaryDTO creator;
    private String name;
    private String description;
    private Long timestampCreated;
    private List<PostSummaryDTO> posts = new ArrayList<>();

    public TopicDTO(TopicDocument topic, List<PostSummaryDTO> posts) {
        this.id = topic.getId();
        this.name = topic.getName();
        this.creator = topic.getCreator();
        this.description = topic.getDescription();
        this.timestampCreated = topic.getTimestampCreated();
        this.posts.addAll(posts);
    }
}
