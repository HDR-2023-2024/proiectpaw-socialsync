package com.socialsync.notifymicroservice.repositories;

import com.socialsync.notifymicroservice.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> getCommentsByPostId (String postId);
}
