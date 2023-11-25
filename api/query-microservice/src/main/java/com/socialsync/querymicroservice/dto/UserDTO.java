package com.socialsync.querymicroservice.dto;

import com.socialsync.querymicroservice.pojo.UserSelect;
import com.socialsync.querymicroservice.pojo.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash
public class UserDTO {
    @Id
    private String id;
    private String  username;
    private RoleType role;

    public UserDTO(UserSelect userSelect) {
        this.id = userSelect.getId();
        this.username = userSelect.getUsername();
        this.role = userSelect.getRole();
    }
}
