package com.socialsync.topicsmicroservice.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@NoArgsConstructor
public class Topic {
    @Id
    private String id;
    private String name;
    private String description;

    private String photoId;

    private String creatorId;

    private Long timestampCreated;
    private Long timestampUpdated;

    public Topic(String name, String description, Boolean ageRestriction, String creatorId) {
        this.name = name;
        this.description = description;
        this.creatorId = creatorId;
    }
}
