package com.socialsync.usersmicroservice.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Credentials {
    private String username;
    private String password;
}
