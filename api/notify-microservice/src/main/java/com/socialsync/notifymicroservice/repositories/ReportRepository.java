package com.socialsync.notifymicroservice.repositories;

import com.socialsync.notifymicroservice.pojo.Post;
import com.socialsync.notifymicroservice.pojo.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
}