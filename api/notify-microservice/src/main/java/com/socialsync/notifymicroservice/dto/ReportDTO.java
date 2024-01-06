package com.socialsync.notifymicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ReportDTO {
    private String userId;
    private String username;
    private String postId;
    private String postTitle;
}
