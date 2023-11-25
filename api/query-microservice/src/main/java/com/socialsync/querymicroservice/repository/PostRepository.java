package com.socialsync.querymicroservice.repository;

import com.socialsync.querymicroservice.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<PostDTO, String> {
    Page<PostDTO> findAll(Pageable pageable);

}
