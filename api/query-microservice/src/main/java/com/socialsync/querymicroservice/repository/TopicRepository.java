package com.socialsync.querymicroservice.repository;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.socialsync.querymicroservice.documents.TopicDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends RedisDocumentRepository<TopicDocument, String> {
    Page<TopicDocument> findAll(Pageable pageable);
    Page<TopicDocument> searchByName(String name, Pageable pageable);
}
