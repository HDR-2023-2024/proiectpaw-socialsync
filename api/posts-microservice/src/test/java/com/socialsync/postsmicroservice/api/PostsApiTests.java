package com.socialsync.postsmicroservice.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.socialsync.postsmicroservice.interfaces.PostRepository;
import com.socialsync.postsmicroservice.pojo.Post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.web.servlet.MockMvc;

import javax.management.Query;

//@WebMvcTest(PostsController.class)
public class PostsApiTests {
//    @Test
//    void It_Retrieves_A_Post() throws Exception {
//        repository.save(mockPost);
//
//        String id = repository.findPostByTitle(mockPost.getTitle()).get().get(0).getId();
//
//        this.mockMvc.perform(get("/api/v1/posts/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.creatorId").value("123"))
//                .andExpect(jsonPath("$.topicId").value("321"))
//                .andExpect(jsonPath("$.title").value("TEST POST"))
//                .andExpect(jsonPath("$.content").value("TEST POST CONTENT"));
//    }
}
