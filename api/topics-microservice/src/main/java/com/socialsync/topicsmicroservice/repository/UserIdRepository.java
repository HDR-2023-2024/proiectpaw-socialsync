package com.socialsync.topicsmicroservice.repository;

import com.socialsync.topicsmicroservice.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdRepository extends MongoRepository<User, String> {
}
