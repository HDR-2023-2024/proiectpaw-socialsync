package com.socialsync.querymicroservice.repository;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.socialsync.querymicroservice.dto.CommentDTO;
import com.socialsync.querymicroservice.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends RedisDocumentRepository<PostDTO, String> {
    Page<PostDTO> findAll(Pageable pageable);
    Page<PostDTO> findAllByTopicId(Pageable pageable, String topicId);

    Page<PostDTO> findAllByCreator_Id(Pageable pageable, String id);
}
