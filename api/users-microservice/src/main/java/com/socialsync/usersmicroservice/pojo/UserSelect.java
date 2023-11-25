package com.socialsync.usersmicroservice.pojo;

import com.socialsync.usersmicroservice.pojo.enums.GenderType;
import com.socialsync.usersmicroservice.pojo.enums.RoleType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class UserSelect {
    private String id;
    private String  username;
    private String  email;
    private RoleType role;
    private GenderType gender;

    public UserSelect(String id, String username, String email, RoleType role, GenderType gender) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.gender = gender;
    }
}
