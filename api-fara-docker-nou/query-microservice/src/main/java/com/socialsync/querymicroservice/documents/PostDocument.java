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

import java.util.HashSet;
import java.util.Set;

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
    private Set<String> upvotes = new HashSet<>();
    private Set<String> downvotes = new HashSet<>();
    private Set<String> photos = new HashSet<>();
    private Long timestampCreated;
    private Long timestampUpdated;

    public PostDocument(Post post) {
        this.id = post.getId();
        this.topicId = post.getTopicId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.score = 0;
        this.timestampCreated = post.getTimestampCreated();
        this.timestampUpdated = post.getTimestampUpdated();
        this.photos = post.getPhotos();
    }

    public void addUpvote(String userId) {
        if (!this.upvotes.contains(userId)) {
            upvotes.add(userId);
            downvotes.remove(userId);
        }
        else
            upvotes.remove(userId);
    }

    public void addDownvote(String userId) {
        if (!this.downvotes.contains(userId)) {
            downvotes.add(userId);
            upvotes.remove(userId);
        }
        else
            downvotes.remove(userId);
    }
}
