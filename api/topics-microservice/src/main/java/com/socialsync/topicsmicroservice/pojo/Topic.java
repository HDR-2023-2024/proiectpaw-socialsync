package com.socialsync.topicsmicroservice.pojo;

import com.socialsync.topicsmicroservice.pojo.enums.Categorie;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Categorie categorie;
    private Set<String> members = new HashSet<>();


    private Long timestampCreated;
    private Long timestampUpdated;

    public Topic(String name, String description, String photoId, String creatorId, Categorie categorie) {
        this.name = name;
        this.description = description;
        this.creatorId = creatorId;
        this.photoId = photoId;
        this.categorie = categorie;
    }

    public Topic(Topic topic) {
        this.name = topic.getName();
        this.description = topic.getDescription();
        this.creatorId = topic.getCreatorId();
        this.photoId = topic.getPhotoId();
        this.categorie = topic.getCategorie();
    }

    public Topic(String id, String creatorId) {
        this.id = id;
        this.creatorId = creatorId;
    }
}
