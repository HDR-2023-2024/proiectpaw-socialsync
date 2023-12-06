package com.socialsync.querymicroservice.pojo;

import lombok.Data;

@Data
public class Post {
    private String id;
    private String creatorId;
    private String topicId;
    private String title;
    private String content;
    private Integer score;
    private Long timestampCreated;
    private Long timestampUpdated;
}
