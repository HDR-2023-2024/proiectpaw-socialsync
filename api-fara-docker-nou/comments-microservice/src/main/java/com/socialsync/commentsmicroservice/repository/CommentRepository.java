package com.socialsync.commentsmicroservice.repository;

import com.socialsync.commentsmicroservice.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
}
