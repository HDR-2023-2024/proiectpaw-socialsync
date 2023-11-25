package com.socialsync.querymicroservice.dto;

import com.redis.om.spring.annotations.Document;
import com.socialsync.querymicroservice.pojo.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class TopicDTO {
    @Id
    private String id;

    @Reference
    private UserDTO creatorId;

    @Indexed
    private String name;
    private String description;
    private Boolean ageRestriction;

    @Reference
    private List<PostDTO> posts;

    @Reference
    private List<UserDTO> members;

    private Long timestampCreated;
    private Long timestampUpdated;

    public TopicDTO(Topic topic) {
        this.id = topic.getId();
        this.name = topic.getName();
        this.description = topic.getDescription();
        this.ageRestriction = topic.getAgeRestriction();
    }
}
