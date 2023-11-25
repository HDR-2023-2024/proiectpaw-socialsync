package com.socialsync.querymicroservice.repository;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.socialsync.querymicroservice.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends RedisDocumentRepository<UserDTO, String> {
    Page<UserDTO> findAll(Pageable pageable);
    Page<UserDTO> searchByUsername(String username, Pageable page);
}
