package com.socialsync.querymicroservice.repository;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.socialsync.querymicroservice.documents.PostDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends RedisDocumentRepository<PostDocument, String> {
    Page<PostDocument> findAll(Pageable pageable);
    Page<PostDocument> findAllByTopicId(String id, Pageable pageable);
}
