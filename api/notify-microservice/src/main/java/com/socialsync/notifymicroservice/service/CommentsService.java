package com.socialsync.notifymicroservice.service;

import com.socialsync.notifymicroservice.pojo.Comment;
import com.socialsync.notifymicroservice.pojo.Post;
import com.socialsync.notifymicroservice.repositories.CommentRepository;
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
    private PostRepository postRepository;

    // Comentariile la postarea a carui creator este user-ul
    public List<Comment> getAllNewCommentUser(String user_id) {
        // 1. Fetch posts by the creator
        List<Post> posts = postRepository.findPostsByCreatorId(user_id);

        // 2. Iterate through the posts and fetch unseen comments for each post
        List<Comment> allUnseenComments = new ArrayList<>();
        for (Post post : posts) {
            List<Comment> postComments = commentRepository.getCommentsByPostId(post.getPostId());

            // 4. Filter unseen comments and add them to the result list
            List<Comment> unseenComments = postComments.stream()
                    .filter(comment -> !comment.getSeen())
                    .collect(Collectors.toList());

            // 3. Update the 'seen' flag for the fetched comments
            for (Comment comment : unseenComments) {
                comment.setSeen(true);
                commentRepository.save(comment);
            }

            allUnseenComments.addAll(unseenComments);
        }

        return allUnseenComments;
    }
}
