package com.socialsync.postsmicroservice.interfaces;

import com.socialsync.postsmicroservice.pojo.Post;
import com.socialsync.postsmicroservice.util.exceptions.PostNotFound;

import java.util.HashMap;

public interface PostsServiceMethods {
    HashMap<String, Post> fetchAllPosts();
    Post fetchPostById(String id) throws PostNotFound;
    void addPost(Post post);
    void updatePost(String id, Post post) throws PostNotFound;
    void deletePost(String id) throws PostNotFound;

    void upvotePost(String postId, String userId) throws PostNotFound;
    void downvotePost(String postId, String userId) throws PostNotFound;
}
