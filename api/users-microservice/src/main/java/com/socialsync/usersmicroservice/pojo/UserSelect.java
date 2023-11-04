package com.socialsync.usersmicroservice.pojo;

import com.socialsync.usersmicroservice.pojo.enums.GenderType;
import com.socialsync.usersmicroservice.pojo.enums.RoleType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "User")
@Getter
@Setter
public class UserSelect {
    @Id
    private  Long id;
    private String  username;
    private String  email;
    private RoleType role;
    private GenderType gender;

    public UserSelect(Long id, String username, String email, RoleType role, GenderType gender) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.gender = gender;
    }

    public  UserSelect(){}
}
