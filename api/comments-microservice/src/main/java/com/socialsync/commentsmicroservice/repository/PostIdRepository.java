package com.socialsync.commentsmicroservice.repository;

import com.socialsync.commentsmicroservice.pojo.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostIdRepository extends MongoRepository<Post, String> {
}
