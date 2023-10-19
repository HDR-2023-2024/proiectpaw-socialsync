package com.example.postsmicroservice.api;

import com.example.postsmicroservice.pojo.Post;
import com.example.postsmicroservice.service.PostsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
@AllArgsConstructor
public class ApiController {

    private final PostsService postsService;

    @GetMapping
    public ResponseEntity<HashMap<String, Post>> fetchAllPosts() {
        return new ResponseEntity<>(postsService.fetchAllPosts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addPost() {
        postsService.addPost();

        return new ResponseEntity<>("Post added", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updatePost() {
        //postsService.updatePost();
        return new ResponseEntity<>("Post updated", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deletePost() {
        return new ResponseEntity<>("Post deleted", HttpStatus.OK);
    }
}
