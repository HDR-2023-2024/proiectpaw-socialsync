package com.socialsync.commentsmicroservice.service;

import com.socialsync.commentsmicroservice.interfaces.CommentsServiceMethods;
import com.socialsync.commentsmicroservice.pojo.Comment;
import com.socialsync.commentsmicroservice.repository.CommentRepository;
import com.socialsync.commentsmicroservice.util.exceptions.CommentNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentsService implements CommentsServiceMethods {

    private CommentRepository repository;

    @Override
    public HashMap<String, Comment> fetchAllComments() {
        HashMap<String, Comment> lista = new HashMap<>();

        List<Comment> comments = repository.findAll();

        for (Comment comment : comments)
            lista.put(comment.getId(), comment);

        return lista;
    }

    @Override
    public Comment fetchCommentById(String id) throws CommentNotFound {
        return repository.findById(id).orElseThrow(() -> new CommentNotFound("Not found: " + id));
    }

    @Override
    public void addComment(Comment comment) {
        comment.setTimestampCreated(Instant.now().getEpochSecond());
        repository.insert(comment);
    }

    @Override
    public void updateComment(String id, Comment comment) throws CommentNotFound {
        repository.findById(id).map(elem -> {
            elem.setContent(comment.getContent());
            elem.setCreatorId(comment.getCreatorId());
            elem.setPostId(comment.getPostId());
            elem.setTimestampUpdated(Instant.now().getEpochSecond());
            repository.save(elem);
            return elem;
        }).orElseThrow(() -> {
            comment.setTimestampCreated(Instant.now().getEpochSecond());
            repository.insert(comment);
            return new CommentNotFound("Comment not found. Created one instead.");
        });
    }

    @Override
    public void deleteComment(String id) {
        repository.deleteById(id);
    }
}
