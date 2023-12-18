package com.socialsync.querymicroservice.pojo;

import lombok.Data;

@Data
public class Topic {
    private String id;
    private String name;
    private String description;
    private Integer photoId;
    private String creatorId;
    private Long timestampCreated;
    private Long timestampUpdated;
}
