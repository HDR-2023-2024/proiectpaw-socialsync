package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.documents.PostDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSummaryDTO {
    private String id;
    private UserSummaryDTO creator;
    private PostTopicSummaryDTO topic;
    private String title;
    private String content;
    private boolean likedByUser = false;
    private boolean dislikedByUser = false;
    private Integer score;
    private Long timestampCreated;
    private String photoId;


    public PostSummaryDTO(PostDocument post, PostTopicSummaryDTO topic) {
        this.id = post.getId();
        this.creator = post.getCreator();
        this.topic = topic;
        this.title = post.getTitle();
        this.content = post.getContent().substring(0, Math.min(post.getContent().length(), 500));
        this.score = post.getUpvotes().size() - post.getDownvotes().size();
        this.timestampCreated = post.getTimestampCreated();
        this.photoId = !post.getPhotos().isEmpty() ? post.getPhotos().iterator().next() : null;
    }
}
