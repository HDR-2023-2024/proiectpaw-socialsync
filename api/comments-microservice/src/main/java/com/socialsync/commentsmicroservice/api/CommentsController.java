package com.socialsync.commentsmicroservice.api;

import com.socialsync.commentsmicroservice.pojo.Comment;
import com.socialsync.commentsmicroservice.service.CommentsService;
import com.socialsync.commentsmicroservice.util.exceptions.CommentNotFound;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api/v1/comments")
@AllArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;

    @GetMapping
    public ResponseEntity<HashMap<String, Comment>> fetchAllComments() {
        return new ResponseEntity<>(commentsService.fetchAllComments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> fetchCommentById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(commentsService.fetchCommentById(id), HttpStatus.OK);
        } catch (CommentNotFound ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        commentsService.addComment(comment);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable String id, @RequestBody Comment comment) {
        try {
            commentsService.updateComment(id, comment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (CommentNotFound ex) {
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable String id) {
        try {
            commentsService.deleteComment(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (CommentNotFound ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
