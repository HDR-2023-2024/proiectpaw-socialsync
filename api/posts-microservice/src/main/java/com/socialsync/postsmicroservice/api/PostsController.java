package com.socialsync.postsmicroservice.api;

import com.socialsync.postsmicroservice.pojo.AuthorizedInfo;
import com.socialsync.postsmicroservice.pojo.Post;
import com.socialsync.postsmicroservice.service.PostsService;
import com.socialsync.postsmicroservice.util.exceptions.PostNotFound;
import com.socialsync.postsmicroservice.util.exceptions.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

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
    public ResponseEntity<?> addPost(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole,@RequestBody Post post) {
            post.setCreatorId(userId);
            post.setId(null);
            postsService.addPost(post);
            return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole, @PathVariable String id, @RequestBody Post post) {
        try {
            post.setId(id);
            post.setCreatorId(userId);
            Post post1 = postsService.fetchPostById(id);
            if (Objects.equals(post1.getCreatorId(), userId)) {
                postsService.updatePost(id, post);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Este postarea altui utilizator!", HttpStatus.UNAUTHORIZED);
            }
        } catch (PostNotFound topicNotFound) {
            System.out.println("Postarea nu exista il creez");
            post.setCreatorId(userId);
            post.setId(null);
            postsService.addPost(post);
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole,@PathVariable String id) {
        try {
            Post post = postsService.fetchPostById(id);
            if (Objects.equals(userRole, "admin") || Objects.equals(userId, post.getCreatorId())) {
                postsService.deletePost(id);
                return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Postarea nu poate fi stersa decat de admin sau utilizatorul care la creat!", HttpStatus.UNAUTHORIZED);
            }
        } catch (PostNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/upvote")
    public ResponseEntity<?> upvotePost(@RequestHeader("X-User-Id") String userId, @PathVariable String id) {
    	System.out.println(userId);
        try {
            postsService.upvotePost(id, userId);
            return ResponseEntity
                    .noContent()
                    .build();
        } catch (PostNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/downvote")
    public ResponseEntity<?> downvotePost(@RequestHeader("X-User-Id") String userId, @PathVariable String id) {
    System.out.println(userId);
        try {
            postsService.downvotePost(id, userId);
            return ResponseEntity
                    .noContent()
                    .build();
        } catch (PostNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
