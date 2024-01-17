package com.socialsync.notifymicroservice.pojo;


import com.socialsync.notifymicroservice.pojo.enums.QueueMessageType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
public class Report {
    @Id
    private String id;
    private String userId;
    private String username;
    private String postId;
    private String postTitle;
}