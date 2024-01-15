package com.socialsync.usersmicroservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthorizedResponseDto {
    private String token;
    private String username;
    private String photoId;
    private String id;
    private String role;
}
