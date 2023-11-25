package com.socialsync.querymicroservice.repository;

import com.socialsync.querymicroservice.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<CommentDTO, String> {
    Page<CommentDTO> findAll(Pageable pageable);
}
