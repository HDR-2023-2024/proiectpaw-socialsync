package com.socialsync.commentsmicroservice.repository;

import com.socialsync.commentsmicroservice.pojo.Post;
import com.socialsync.commentsmicroservice.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdRepository extends MongoRepository<User, String> {
}
