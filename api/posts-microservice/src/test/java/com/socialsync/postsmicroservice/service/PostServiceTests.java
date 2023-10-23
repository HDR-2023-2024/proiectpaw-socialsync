package com.socialsync.postsmicroservice.service;

import com.socialsync.postsmicroservice.api.PostsController;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostServiceTests {

    @Autowired
    private PostsService postsService;

    @Autowired
    private PostsController controller;

    @Test
    void Context_Loads() {
        assertThat(postsService).isNotNull();
        assertThat(controller).isNotNull();
    }
}
