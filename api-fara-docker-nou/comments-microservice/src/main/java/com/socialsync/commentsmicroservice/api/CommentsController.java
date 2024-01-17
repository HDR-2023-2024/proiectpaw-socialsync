package com.socialsync.commentsmicroservice.api;

import com.socialsync.commentsmicroservice.pojo.AuthorizedInfo;
import com.socialsync.commentsmicroservice.pojo.Comment;
import com.socialsync.commentsmicroservice.service.AuthorizationService;
import com.socialsync.commentsmicroservice.service.CommentsService;
import com.socialsync.commentsmicroservice.util.exceptions.CommentNotFound;
import com.socialsync.commentsmicroservice.util.exceptions.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/comments")
@AllArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;
    private final AuthorizationService authorizationService;

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
    public ResponseEntity<?> addComment(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole, @RequestBody Comment comment) {
            comment.setCreatorId(userId);
            comment.setId(null);
            commentsService.addComment(comment);
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole, @PathVariable String id, @RequestBody Comment comment) {
        try {
            Comment comment1 = commentsService.fetchCommentById(id);
            if (Objects.equals(comment1.getCreatorId(),userId)) {
                comment.setCreatorId(userId);
                commentsService.updateComment(id, comment);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Acest comentariu apartine altui utilizator!", HttpStatus.UNAUTHORIZED);
            }
        } catch (CommentNotFound topicNotFound) {
            System.out.println("Topicul nu exista il creez");
            comment.setCreatorId(userId);
            comment.setId(null);
            commentsService.addComment(comment);
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String userRole, @PathVariable String id) {
        try {
            Comment comment = commentsService.fetchCommentById(id);
            if (Objects.equals(userRole, "admin") || Objects.equals(userId, comment.getCreatorId())) {
                commentsService.deleteComment(id);
                return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Comentariul nu poate fi sters decat de admin sau utilizatorul care l-a creat!", HttpStatus.UNAUTHORIZED);
            }
        } catch (CommentNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
