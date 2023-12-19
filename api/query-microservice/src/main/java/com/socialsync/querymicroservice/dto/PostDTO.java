package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.documents.PostDocument;
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
public class PostDTO {
    private String id;
    private UserSummaryDTO creator;
    private PostTopicSummaryDTO topic;
    private String title;
    private String content;
    private boolean likedByUser = false;
    private boolean dislikedByUser = false;
    private List<CommentDTO> comments = new ArrayList<>();
    private Set<String> photos = new HashSet<>();
    private Integer score;
    private Long timestampCreated;


    public PostDTO(PostDocument post, List<CommentDTO> comments, PostTopicSummaryDTO topic) {
        this.id = post.getId();
        this.creator = post.getCreator();
        this.topic = topic;
        this.title = post.getTitle();
        this.content = post.getContent();
        this.photos = post.getPhotos();
        this.score = post.getUpvotes().size() - post.getDownvotes().size();
        this.comments.addAll(comments);
        this.timestampCreated = post.getTimestampCreated();
    }
}
