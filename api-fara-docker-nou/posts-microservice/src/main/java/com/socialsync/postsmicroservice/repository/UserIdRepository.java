package com.socialsync.postsmicroservice.repository;

import com.socialsync.postsmicroservice.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdRepository extends MongoRepository<User, String> {
}
