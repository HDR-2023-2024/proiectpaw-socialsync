package com.socialsync.querymicroservice.repository;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.socialsync.querymicroservice.documents.CommentDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends RedisDocumentRepository<CommentDocument, String> {
    Page<CommentDocument> findAll(Pageable pageable);
    Page<CommentDocument> findAllByPostId(String id, Pageable pageable);
}
