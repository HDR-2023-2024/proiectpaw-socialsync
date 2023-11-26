package com.socialsync.commentsmicroservice.interfaces;

import com.socialsync.commentsmicroservice.pojo.Comment;
import com.socialsync.commentsmicroservice.util.exceptions.CommentNotFound;

import java.util.HashMap;

public interface CommentsServiceMethods {
    HashMap<String, Comment> fetchAllComments();
    Comment fetchCommentById(String id) throws CommentNotFound;
    void addComment(Comment comment);
    void updateComment(String id, Comment comment) throws CommentNotFound;
    void deleteComment(String id) throws CommentNotFound;
}
