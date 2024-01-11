package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.documents.TopicDocument;
import com.socialsync.querymicroservice.pojo.enums.Categorie;
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
    private String photoId;
    private String description;
    private Categorie categorie;
    private Integer members;

    private Long timestampCreated;

    public TopicSummaryDTO(TopicDocument topic) {
        this.id = topic.getId();
        this.creator = topic.getCreator();
        this.name = topic.getName();
        this.photoId = topic.getPhotoId();
        this.description = topic.getDescription();
        this.categorie = topic.getCategorie();
        this.members = topic.getMembers().size();
        this.timestampCreated = topic.getTimestampCreated();
    }
}
