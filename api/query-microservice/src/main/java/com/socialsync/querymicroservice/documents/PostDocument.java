package com.socialsync.querymicroservice.documents;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import com.socialsync.querymicroservice.dto.UserSummaryDTO;
import com.socialsync.querymicroservice.pojo.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class PostDocument {
    @Id
    private String id;
    @Indexed
    private UserSummaryDTO creator;
    @Indexed
    private String topicId;
    @Searchable
    private String title;
    private String content;
    private Integer score;
    private Long timestampCreated;
    private Long timestampUpdated;

    public PostDocument(Post post) {
        this.id = post.getId();
        this.topicId = post.getTopicId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.score = post.getScore();
        this.timestampCreated = post.getTimestampCreated();
        this.timestampUpdated = post.getTimestampUpdated();
    }
}
