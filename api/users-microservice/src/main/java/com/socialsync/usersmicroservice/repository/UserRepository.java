package com.socialsync.usersmicroservice.repository;

import com.socialsync.usersmicroservice.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

}
