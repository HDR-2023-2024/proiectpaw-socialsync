package com.socialsync.querymicroservice.repository;

import com.socialsync.querymicroservice.dto.TopicDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<TopicDTO, String> {
    Page<TopicDTO> findAll(Pageable pageable);
}
