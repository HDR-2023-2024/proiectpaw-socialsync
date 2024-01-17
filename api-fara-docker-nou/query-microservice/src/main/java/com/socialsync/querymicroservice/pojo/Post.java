package com.socialsync.querymicroservice.pojo;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Post {
    private String id;
    private String creatorId;
    private String topicId;
    private String title;
    private String content;
    private Set<String> photos = new HashSet<>();
    private Integer score;
    private Long timestampCreated;
    private Long timestampUpdated;
}
