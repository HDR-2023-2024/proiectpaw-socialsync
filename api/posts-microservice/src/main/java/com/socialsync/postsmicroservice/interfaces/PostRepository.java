package com.socialsync.postsmicroservice.interfaces;

import com.socialsync.postsmicroservice.pojo.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
