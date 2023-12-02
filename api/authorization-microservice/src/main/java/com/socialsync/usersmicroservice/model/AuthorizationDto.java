package com.socialsync.usersmicroservice.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorizationDto {
    private String url;
    private String method;
    private String token;
    private String id;
}
