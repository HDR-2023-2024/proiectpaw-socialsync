package com.socialsync.querymicroservice.repository;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.socialsync.querymicroservice.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends RedisDocumentRepository<CommentDTO, String> {
    Page<CommentDTO> findAll(Pageable pageable);
    Page<CommentDTO> findAllByPostId(Pageable pageable, String postId);
}
