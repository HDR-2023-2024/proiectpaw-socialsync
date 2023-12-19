package com.socialsync.notifymicroservice.repositories;

import com.socialsync.notifymicroservice.pojo.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findPostsByCreatorId(String creator_id); // toate postarile cuiva
    List<Post> findPostsByPostId( String post_id);
}
