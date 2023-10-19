package com.example.postsmicroservice.interfaces;

import com.example.postsmicroservice.pojo.Post;
import jdk.jshell.spi.ExecutionControl;

import java.util.HashMap;
import java.util.List;

public interface IPostsService {
    abstract public HashMap<String, Post> fetchAllPosts();
    abstract public void addPost();
    abstract public void updatePost(Post post);
    abstract public void deletePost(String id);
}
