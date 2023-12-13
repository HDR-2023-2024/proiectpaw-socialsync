package com.socialsync.querymicroservice.repository;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.socialsync.querymicroservice.documents.UserDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends RedisDocumentRepository<UserDocument, String> {
    Page<UserDocument> findAll(Pageable pageable);
    Page<UserDocument> searchByUsername(String username, Pageable pageable);
}
