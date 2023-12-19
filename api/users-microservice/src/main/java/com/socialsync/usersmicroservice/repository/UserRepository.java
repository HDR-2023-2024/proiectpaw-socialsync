package com.socialsync.usersmicroservice.repository;

import com.socialsync.usersmicroservice.pojo.User;
import com.socialsync.usersmicroservice.pojo.enums.GenderType;
import com.socialsync.usersmicroservice.pojo.enums.RoleType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.beans.Transient;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUsername(String s);

    @Modifying
    @Query("UPDATE User u SET u.username = :username, u.email = :email, u.role = :role, u.gender = :gender, u.description = :description, u.photoId = :photoId WHERE u.id = :id")
    @Transactional
    void updateUser(@Param("id") String id, @Param("username") String username, @Param("email") String email, @Param("role") RoleType role, @Param("gender") GenderType gender, @Param("description") String description, @Param("photoId") String photoId);


    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    @Transactional
    void updatePassword(@Param("id") String id, @Param("password") String password);
}
