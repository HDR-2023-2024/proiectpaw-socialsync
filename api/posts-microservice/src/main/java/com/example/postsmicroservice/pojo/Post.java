package com.example.postsmicroservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Post {
    private String id;
    private String creatorId;
    private String topicId;
    private String title;
    private String content;
    private Integer score = 0;
    private Long timestampCreated;
    private Long timestampUpdated;
}
