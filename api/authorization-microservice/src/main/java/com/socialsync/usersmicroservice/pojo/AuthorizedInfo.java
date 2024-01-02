package com.socialsync.usersmicroservice.pojo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
public class AuthorizedInfo {
    private String id;
    private String role;
}
