package com.socialsync.postsmicroservice.api;

import com.socialsync.postsmicroservice.pojo.AuthorizedInfo;
import com.socialsync.postsmicroservice.pojo.Post;
import com.socialsync.postsmicroservice.service.AuthorizationService;
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
    private final AuthorizationService authorizationService;

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
    public ResponseEntity<?> addPost(@RequestHeader("Authorization") String authorizationHeader,@RequestBody Post post) {
        try {
            AuthorizedInfo authorizedInfo = authorizationService.authorized(authorizationHeader);
            post.setCreatorId(authorizedInfo.getId());
            post.setId(null);
            postsService.addPost(post);
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String id, @RequestBody Post post) {
        AuthorizedInfo authorizedInfo = null;
        try {
            authorizedInfo = authorizationService.authorized(authorizationHeader);
            post.setId(id);
            post.setCreatorId(authorizedInfo.getId());
            Post post1 = postsService.fetchPostById(id);
            if (Objects.equals(post1.getCreatorId(), authorizedInfo.getId())) {
                postsService.updatePost(id, post);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Este postarea altui utilizator!", HttpStatus.UNAUTHORIZED);
            }
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (PostNotFound topicNotFound) {
            System.out.println("Postarea nu exista il creez");
            post.setCreatorId(authorizedInfo.getId());
            post.setId(null);
            postsService.addPost(post);
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@RequestHeader("Authorization") String authorizationHeader,@PathVariable String id) {
        try {
            AuthorizedInfo authorizedInfo = authorizationService.authorized(authorizationHeader);
            Post post = postsService.fetchPostById(id);
            if (Objects.equals(authorizedInfo.getRole(), "admin") || Objects.equals(authorizedInfo.getId(), post.getCreatorId())) {
                postsService.deletePost(id);
                return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Postarea nu poate fi stersa decat de admin sau utilizatorul care la creat!", HttpStatus.UNAUTHORIZED);
            }
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (PostNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
