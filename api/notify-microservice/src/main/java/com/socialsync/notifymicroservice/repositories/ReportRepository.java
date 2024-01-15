package com.socialsync.notifymicroservice.repositories;

import com.socialsync.notifymicroservice.pojo.Post;
import com.socialsync.notifymicroservice.pojo.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
    Optional<Report> findAllByPostIdAndUserId (String postId, String userId); // nu se poate face report de mai multe ori la o postare
}