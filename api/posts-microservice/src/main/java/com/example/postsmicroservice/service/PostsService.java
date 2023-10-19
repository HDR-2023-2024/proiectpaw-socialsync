package com.example.postsmicroservice.service;

import com.example.postsmicroservice.interfaces.IPostsService;
import com.example.postsmicroservice.pojo.Post;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Service
public class PostsService implements IPostsService {
    @Override
    public HashMap<String, Post> fetchAllPosts() {
        HashMap<String, Post> lista = new HashMap<String, Post>();

        lista.put("1", new Post("1", "1", "1", "test", "test", 100, Instant.now().getEpochSecond(), null));
        return lista;
    }

    @Override
    public void addPost()  {

    }

    @Override
    public void updatePost(Post post) {

    }

    @Override
    public void deletePost(String id) {

    }
}
