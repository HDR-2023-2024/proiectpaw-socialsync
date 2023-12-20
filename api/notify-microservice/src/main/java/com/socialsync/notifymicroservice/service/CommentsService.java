package com.socialsync.notifymicroservice.service;

import com.socialsync.notifymicroservice.pojo.Comment;
import com.socialsync.notifymicroservice.pojo.CommentQueueMessage;
import com.socialsync.notifymicroservice.pojo.PersistentPost;
import com.socialsync.notifymicroservice.pojo.Post;
import com.socialsync.notifymicroservice.repositories.CommentRepository;
import com.socialsync.notifymicroservice.repositories.PersistentPostRepository;
import com.socialsync.notifymicroservice.repositories.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CommentsService {
    private CommentRepository commentRepository;
    private PersistentPostRepository persistentPostRepository;

    // Comentariile la postarea a carui creator este user-ul
    public List<Comment> getAllNewCommentUser(String user_id) {
        // 1. Fetch posts by the creator
        List<PersistentPost> posts = persistentPostRepository.getPersistentPostsByCreatorId(user_id);

        // 2. Iterate through the posts and fetch unseen comments for each post
        List<Comment> allUnseenComments = new ArrayList<>();
        for (PersistentPost post : posts) {
            List<Comment> postComments = commentRepository.getCommentsByPostId(post.getPostId());
            allUnseenComments.addAll(postComments);
        }
        // 3. Delete the fetched comments
        commentRepository.deleteAll(allUnseenComments);

        return allUnseenComments;
    }

    public void saveComment(CommentQueueMessage commentQueueMessage) {
        Comment comment = new Comment();
        comment.setComm(commentQueueMessage.getComm());
        comment.setPostId(commentQueueMessage.getPost_id());
        comment.setUserId(commentQueueMessage.getUser_id());
        // agregare pe titlu
        log.info("Post id comm" + commentQueueMessage.getPost_id());
        comment.setPostTitle(persistentPostRepository.getPersistentPostByPostId(commentQueueMessage.getPost_id()).getTitle());

        commentRepository.save(comment);
    }
}
