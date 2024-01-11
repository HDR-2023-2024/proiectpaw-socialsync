package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.documents.TopicDocument;
import com.socialsync.querymicroservice.pojo.enums.Categorie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDTO {
    private String id;
    private UserSummaryDTO creator;
    private String name;
    private String description;
    private String photoId;
    private Categorie categorie;
    private Set<UserSummaryDTO> members = new HashSet<>();

    private Long timestampCreated;
    private List<PostSummaryDTO> posts = new ArrayList<>();

    public TopicDTO(TopicDocument topic, List<PostSummaryDTO> posts, Set<UserSummaryDTO> members ) {
        this.id = topic.getId();
        this.name = topic.getName();
        this.creator = topic.getCreator();
        this.photoId = topic.getPhotoId();
        this.description = topic.getDescription();
        this.categorie = topic.getCategorie();
        this.timestampCreated = topic.getTimestampCreated();
        this.posts.addAll(posts);
        this.members.addAll(members);
    }
}
