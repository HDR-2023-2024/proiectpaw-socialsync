package com.socialsync.querymicroservice.documents;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import com.socialsync.querymicroservice.dto.UserSummaryDTO;
import com.socialsync.querymicroservice.pojo.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class TopicDocument {
    @Id
    private String id;
    @Indexed
    private UserSummaryDTO creator;
    @Searchable
    private String name;
    private String description;
    private String photoId;
    private Long timestampCreated;
    private Long timestampUpdated;

    public TopicDocument(Topic topic) {
        this.id = topic.getId();
        this.name = topic.getName();
        this.description = topic.getDescription();
        this.photoId = topic.getPhotoId();
        this.timestampCreated = topic.getTimestampCreated();
        this.timestampUpdated = topic.getTimestampUpdated();
    }
}
