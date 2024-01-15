package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.documents.UserDocument;
import com.socialsync.querymicroservice.pojo.enums.GenderType;
import com.socialsync.querymicroservice.pojo.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String photoId;
    private String  username;
    private String  email;
    private String  description;
    private RoleType role;
    private GenderType gender;

    public UserDTO(UserDocument user) {
        this.id = user.getId();
        this.photoId =user.getPhotoId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.gender = user.getGender();
        this.description = user.getDescription();
    }
}
