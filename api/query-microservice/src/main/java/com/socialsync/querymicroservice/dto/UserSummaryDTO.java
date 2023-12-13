package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.documents.UserDocument;
import com.socialsync.querymicroservice.pojo.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryDTO {
    private String id;
    private String  username;
    private RoleType role;

    public UserSummaryDTO(UserDocument user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
