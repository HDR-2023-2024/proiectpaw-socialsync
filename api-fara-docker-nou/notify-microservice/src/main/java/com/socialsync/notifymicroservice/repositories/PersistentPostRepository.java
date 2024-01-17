package com.socialsync.notifymicroservice.repositories;

import com.socialsync.notifymicroservice.pojo.PersistentPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersistentPostRepository extends MongoRepository<PersistentPost, String> {
    PersistentPost getPersistentPostByPostId(String postId);
    List<PersistentPost> getPersistentPostsByCreatorId(String creatorId);
}
