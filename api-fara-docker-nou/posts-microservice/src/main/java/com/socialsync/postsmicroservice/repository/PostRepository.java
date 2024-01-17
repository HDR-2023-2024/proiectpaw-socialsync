package com.socialsync.postsmicroservice.repository;

import com.socialsync.postsmicroservice.pojo.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Optional<List<Post>> findPostByTitle(String title);
}
