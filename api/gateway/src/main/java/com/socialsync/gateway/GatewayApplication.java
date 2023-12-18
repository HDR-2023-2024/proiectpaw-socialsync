package com.socialsync.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("get_post_by_id", r -> r
                        .path("/api/v1/posts/{id}")
                        .and()
                        .method("GET")
                        .uri("http://localhost:8080")
                )
                .route("get_posts", r -> r
                        .path("/api/v1/posts")
                        .and()
                        .method("GET")
                        .uri("http://localhost:8080")
                )
                .route("create_post", r -> r
                        .method("POST")
                        .and()
                        .path("/api/v1/posts")
                        .uri("http://localhost:8080")
                )
                .route("update_post", r -> r
                        .method("PUT")
                        .and()
                        .path("/api/v1/posts/{id}")
                        .uri("http://localhost:8080")
                )
                .route("delete_post", r -> r
                        .method("DELETE")
                        .and()
                        .path("/api/v1/posts/{id}")
                        .uri("http://localhost:8080")
                )
                .route("get_comment_by_id", r -> r
                        .path("/api/v1/comments/{id}")
                        .and()
                        .method("GET")
                        .uri("http://localhost:8081")
                )
                .route("get_comments", r -> r
                        .path("/api/v1/comments")
                        .and()
                        .method("GET")
                        .uri("http://localhost:8081")
                )
                .route("create_comment", r -> r
                        .method("POST")
                        .and()
                        .path("/api/v1/comments")
                        .uri("http://localhost:8081")
                )
                .route("update_comment", r -> r
                        .method("PUT")
                        .and()
                        .path("/api/v1/comments/{id}")
                        .uri("http://localhost:8081")
                )
                .route("delete_comment", r -> r
                        .method("DELETE")
                        .and()
                        .path("/api/v1/comments/{id}")
                        .uri("http://localhost:8081")
                )
                .route("get_user_by_id", r -> r
                        .path("/api/v1/users/{id}")
                        .and()
                        .method("GET")
                        .uri("http://localhost:8082")
                )
                .route("get_users", r -> r
                        .path("/api/v1/users")
                        .and()
                        .method("GET")
                        .uri("http://localhost:8082")
                )
                .route("create_user", r -> r
                        .method("POST")
                        .and()
                        .path("/api/v1/users")
                        .uri("http://localhost:8082")
                )
                .route("login_user", r -> r
                        .method("POST")
                        .and()
                        .path("/api/v1/users/login")
                        .uri("http://localhost:8082")
                ).route("update_password", r -> r
                        .method("PUT")
                        .and()
                        .path("/api/v1/users/updatePassword")
                        .uri("http://localhost:8082")
                )
                .route("update_user", r -> r
                        .method("PUT")
                        .and()
                        .path("/api/v1/users")
                        .uri("http://localhost:8082")
                )
                .route("delete_user", r -> r
                        .method("DELETE")
                        .and()
                        .path("/api/v1/users/{id}")
                        .uri("http://localhost:8082")
                )
                .route("get_topic_by_id", r -> r
                        .path("/api/v1/topics/{id}")
                        .and()
                        .method("GET")
                        .uri("http://localhost:8084")
                )
                .route("get_topics", r -> r
                        .path("/api/v1/topics")
                        .and()
                        .method("GET")
                        .uri("http://localhost:8084")
                )
                .route("create_topic", r -> r
                        .method("POST")
                        .and()
                        .path("/api/v1/topics")
                        .uri("http://localhost:8084")
                )
                .route("update_topic", r -> r
                        .method("PUT")
                        .and()
                        .path("/api/v1/topics/{id}")
                        .uri("http://localhost:8084")
                )
                .route("delete_topic", r -> r
                        .method("DELETE")
                        .and()
                        .path("/api/v1/topics/{id}")
                        .uri("http://localhost:8084")
                )
                .route("query_users", r -> r.method("GET")
                        .and()
                        .path("/api/v1/query/users")
                        .filters(f -> f.rewritePath("/api/v1/query/users\\?page=(?<page>\\d+)&username=(?<username>[^&]*)", "/api/v1/query/users?page=${page}&username=${username}"))
                        .uri("http://localhost:8085")
                )
                .route("query_user_id_posts", r -> r.method("GET")
                        .and()
                        .path("/api/v1/query/user/{id}/posts")
                        .filters(f -> f.rewritePath("/api/v1/query/user/(?<id>[^/]+)/posts\\?page=(?<page>\\d+)", "/api/v1/query/user/{id}/posts?page=${page}"))
                        .uri("http://localhost:8085")
                )
                .route("query_user_id_comments", r -> r.method("GET")
                        .and()
                        .path("/api/v1/query/user/{id}/comments")
                        .filters(f -> f.rewritePath("/api/v1/query/user/(?<id>[^/]+)/comments\\?page=(?<page>\\d+)", "/api/v1/query/user/{id}/comments?page=${page}"))
                        .uri("http://localhost:8085")
                )
                .route("query_topics", r -> r.method("GET")
                        .and()
                        .path("/api/v1/query/topics")
                        .filters(f -> f.rewritePath("/api/v1/query/topics\\?page=(?<page>\\d+)&name=(?<name>[^&]*)", "/api/v1/query/topics?page=${page}&name=${name}"))
                        .uri("http://localhost:8085")
                )
                .route("query_topic_id_posts", r -> r.method("GET")
                        .and()
                        .path("/api/v1/query/topic/{id}/posts")
                        .filters(f -> f.rewritePath("/api/v1/query/topic/(?<id>[^/]+)/posts\\?page=(?<page>\\d+)", "/api/v1/query/topic/{id}/posts?page=${page}"))
                        .uri("http://localhost:8085")
                )
                .route("query_posts", r -> r.method("GET")
                        .and()
                        .path("/api/v1/query/posts")
                        .filters(f -> f.rewritePath("/api/v1/query/posts\\?page=(?<page>\\d+)", "/api/v1/query/posts?page=${page}"))
                        .uri("http://localhost:8085")
                )
                .route("query_post_id_comments", r -> r.method("GET")
                        .and()
                        .path("/api/v1/query/post/{id}/comments")
                        .filters(f -> f.rewritePath("/api/v1/query/post/(?<id>[^/]+)/comments\\?page=(?<page>\\d+)", "/api/v1/query/post/${id}/comments?page=${page}"))
                        .uri("http://localhost:8085")
                )
                .route("query_comments", r -> r.method("GET")
                        .and()
                        .path("/api/v1/query/comments")
                        .filters(f -> f.rewritePath("/api/v1/query/comments\\?page=(?<page>\\d+)", "/api/v1/query/comments?page=${page}"))
                        .uri("http://localhost:8085")
                )
                .route("storage", r -> r
                        .path("/api/v1/storage/**")
                        .uri("http://localhost:8088"))
                .route("comments", r -> r
                    .path("/api/v1/comments/**")
                    .uri("http://localhost:8085"))
                .route("posts", r -> r
                    .path("/api/v1/query/posts/**")
                    .uri("http://localhost:8085"))
                .route("posts", r -> r
                        .path("/api/v1/query/topics/**")
                        .uri("http://localhost:8085"))
                .build();

    }
}
