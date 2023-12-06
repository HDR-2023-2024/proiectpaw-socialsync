package com.socialsync.querymicroservice;

import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRedisDocumentRepositories
@SpringBootApplication
public class QueryMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueryMicroserviceApplication.class, args);
	}
}
