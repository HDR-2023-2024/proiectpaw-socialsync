package com.socialsync.usersmicroservice.pojo;

import com.socialsync.usersmicroservice.pojo.enums.GenderType;
import com.socialsync.usersmicroservice.pojo.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "uk_username", columnNames = {"username"}),
        @UniqueConstraint(name = "uk_email", columnNames = {"email"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "gender", length = 1)
    private GenderType gender;

    @Column(name = "photoId", length = 100)
    private String photoId;

    @Column(name = "role", length = 1)
    private RoleType role;

    public User(String username, String password, String email, GenderType gender, String photoId, RoleType role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.photoId = photoId;
        this.role = role;
    }

}
