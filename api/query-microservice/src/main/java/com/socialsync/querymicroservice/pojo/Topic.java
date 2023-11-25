package com.socialsync.querymicroservice.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Topic {
    private String id;
    private String name;
    private String description;
    private Boolean ageRestriction;

    private Integer photoId;

    private List<String> postIds;

    private String creatorId;
    private List<String> memberIds;

    private Long timestampCreated;
    private Long timestampUpdated;
}
