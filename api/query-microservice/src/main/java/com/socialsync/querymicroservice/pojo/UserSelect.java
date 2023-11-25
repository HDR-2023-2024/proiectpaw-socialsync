package com.socialsync.querymicroservice.pojo;

import com.socialsync.querymicroservice.pojo.enums.GenderType;
import com.socialsync.querymicroservice.pojo.enums.RoleType;
import lombok.Data;

@Data
public class UserSelect {
    private String id;
    private String  username;
    private String  email;
    private RoleType role;
    private GenderType gender;
}
