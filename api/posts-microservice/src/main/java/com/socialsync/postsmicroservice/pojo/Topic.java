package com.socialsync.postsmicroservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Document
public class Topic {
    @Id
    String id;
}
