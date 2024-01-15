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
    private String photoId;
    private String  username;
    private String  description;
    private RoleType role;

    public UserSummaryDTO(UserDocument user) {
        this.id = user.getId();
        this.photoId = user.getPhotoId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.description = user.getDescription();
    }
}
