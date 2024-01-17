package com.socialsync.querymicroservice.documents;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import com.socialsync.querymicroservice.pojo.User;
import com.socialsync.querymicroservice.pojo.enums.GenderType;
import com.socialsync.querymicroservice.pojo.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class UserDocument {
    @Id
    private String id;
    private String photoId;

    @Searchable
    private String  username;
    @Indexed
    private String  email;
    private String  description;
    private RoleType role;
    private GenderType gender;

    public UserDocument(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.photoId = user.getPhotoId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.gender = user.getGender();
        this.description = user.getDescription();
    }
}
