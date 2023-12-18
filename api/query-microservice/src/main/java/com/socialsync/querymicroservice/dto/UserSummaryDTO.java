package com.socialsync.querymicroservice.dto;

import com.redis.om.spring.annotations.Indexed;
import com.socialsync.querymicroservice.documents.UserDocument;
import com.socialsync.querymicroservice.pojo.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryDTO {
    @Indexed private String id;
    @Indexed private String photoId;
    @Indexed private String  username;
    @Indexed private RoleType role;

    public UserSummaryDTO(UserDocument user) {
        this.id = user.getId();
        this.photoId = user.getPhotoId();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
