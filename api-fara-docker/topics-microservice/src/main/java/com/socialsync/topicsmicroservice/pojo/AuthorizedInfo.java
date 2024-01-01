package com.socialsync.topicsmicroservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthorizedInfo {
    private String id;
    private String role;
}
