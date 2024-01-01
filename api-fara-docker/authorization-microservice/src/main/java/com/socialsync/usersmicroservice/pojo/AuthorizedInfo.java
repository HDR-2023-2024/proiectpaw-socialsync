package com.socialsync.usersmicroservice.pojo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AuthorizedInfo {
    private String id;
    private String role;
}
