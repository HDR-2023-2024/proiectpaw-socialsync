package com.socialsync.querymicroservice.pojo;

import com.socialsync.querymicroservice.pojo.enums.Categorie;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Topic {
    private String id;
    private String name;
    private String description;
    private String photoId;
    private String creatorId;
    private Categorie categorie;
    private Set<String> members = new HashSet<>();

    private Long timestampCreated;
    private Long timestampUpdated;
}
