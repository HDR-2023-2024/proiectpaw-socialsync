package com.socialsync.querymicroservice.repository;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.socialsync.querymicroservice.dto.TopicDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopicRepository extends RedisDocumentRepository<TopicDTO, String> {
    Page<TopicDTO> findAll(Pageable pageable);
    List<TopicDTO> searchByName(String name);
}
