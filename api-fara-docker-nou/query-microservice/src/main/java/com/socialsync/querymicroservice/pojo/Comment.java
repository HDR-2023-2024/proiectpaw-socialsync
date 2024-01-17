package com.socialsync.querymicroservice.pojo;

import lombok.Data;

@Data
public class Comment {
    private String id;
    private String creatorId;
    private String postId;
    private String content;
    private Integer score = 0;
    private Long timestampCreated;
    private Long timestampUpdated;
}
