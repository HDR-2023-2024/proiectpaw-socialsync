package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.pojo.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash
public class PostDTO {
    @Id
    private String id;
    private String creatorId;
    private String topicId;
    private String title;
    private String content;
    private Integer score;

    @Reference
    private Set<CommentDTO> comments = new HashSet<>();
    private Long timestampCreated;
    private Long timestampUpdated;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.creatorId = post.getCreatorId();
        this.topicId = post.getTopicId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.score = post.getScore();
        this.timestampCreated = post.getTimestampCreated();
        this.timestampUpdated = post.getTimestampUpdated();
    }

    public void addComment(CommentDTO commentDTO) {
        comments.add(commentDTO);
    }

    public void removeComment(CommentDTO commentDTO) {
        comments.remove(commentDTO);
    }
}
