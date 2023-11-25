package com.socialsync.querymicroservice.repository;

import com.socialsync.querymicroservice.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDTO, String> {
    Page<UserDTO> findAll(Pageable pageable);
}
