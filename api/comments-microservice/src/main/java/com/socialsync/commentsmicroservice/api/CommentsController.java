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
    public ResponseEntity<?> addComment(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Comment comment) {
        try {
            AuthorizedInfo authorizedInfo = authorizationService.authorized(authorizationHeader);
            comment.setCreatorId(authorizedInfo.getId());
            comment.setId(null);
            commentsService.addComment(comment);
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String id, @RequestBody Comment comment) {
        AuthorizedInfo authorizedInfo = null;
        try {
            authorizedInfo = authorizationService.authorized(authorizationHeader);
            Comment comment1 = commentsService.fetchCommentById(id);
            if (Objects.equals(comment1.getCreatorId(), authorizedInfo.getId())) {
                comment.setCreatorId(authorizedInfo.getId());
                commentsService.updateComment(id, comment);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Este topicul altui utilizator!", HttpStatus.UNAUTHORIZED);
            }
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (CommentNotFound topicNotFound) {
            System.out.println("Topicul nu exista il creez");
            comment.setCreatorId(authorizedInfo.getId());
            comment.setId(null);
            commentsService.addComment(comment);
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String id) {
        try {
            AuthorizedInfo authorizedInfo = authorizationService.authorized(authorizationHeader);
            Comment comment = commentsService.fetchCommentById(id);
            if (Objects.equals(authorizedInfo.getRole(), "admin") || Objects.equals(authorizedInfo.getId(), comment.getCreatorId())) {
                commentsService.deleteComment(id);
                return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Comentatiul nu poate fi sters decat de admin sau utilizatorul care la creat!", HttpStatus.UNAUTHORIZED);
            }
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (CommentNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
