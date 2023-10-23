package com.socialsync.postsmicroservice.api;

import com.socialsync.postsmicroservice.pojo.Post;
import com.socialsync.postsmicroservice.service.PostsService;
import com.socialsync.postsmicroservice.util.exceptions.PostNotFound;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api/v1/posts")
@AllArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @GetMapping
    public ResponseEntity<HashMap<String, Post>> fetchAllPosts() {
        return new ResponseEntity<>(postsService.fetchAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> fetchPostById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(postsService.fetchPostById(id), HttpStatus.OK);
        } catch (PostNotFound ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> addPost(@RequestBody Post post) {
        postsService.addPost(post);

        return new ResponseEntity<>("Added post: " + post.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable String id, @RequestBody Post post) {
        try {
            postsService.updatePost(id, post);
            return new ResponseEntity<>("Updated post: " + id , HttpStatus.OK);
        } catch (PostNotFound ex) {
            return new ResponseEntity<>(ex.getMessage() + " " + post.getId(), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id) {
        postsService.deletePost(id);
        return new ResponseEntity<>("Post deleted: " + id, HttpStatus.OK);
    }
}
