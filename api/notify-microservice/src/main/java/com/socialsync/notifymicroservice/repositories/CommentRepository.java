package com.socialsync.notifymicroservice.repositories;

import com.socialsync.notifymicroservice.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> getCommentsByPostId (String postId);
}
