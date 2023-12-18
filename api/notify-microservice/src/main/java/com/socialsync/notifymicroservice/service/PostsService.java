package com.socialsync.notifymicroservice.service;

import com.socialsync.notifymicroservice.pojo.Comment;
import com.socialsync.notifymicroservice.pojo.Post;
import com.socialsync.notifymicroservice.pojo.PostQueueMessage;
import com.socialsync.notifymicroservice.repositories.CommentRepository;
import com.socialsync.notifymicroservice.repositories.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostsService {
    private PostRepository postRepository;

    // Notificarile nevazute ale unui user pentru postarile create de el
    public List<Post> getAllNewPostNotifUser (String user_id) {
        List<Post> userPosts = postRepository.findPostsByCreatorId(user_id);

        List<Post> unseenPosts = userPosts.stream()
                .filter(post -> !post.getSeen())
                .collect(Collectors.toList());

        for (Post post : unseenPosts) {
            post.setSeen(true);
            postRepository.save(post);
        }

        return unseenPosts;
    }

    // numele unei postari dupa id
    public String getPostName (String post_id) {
        return postRepository.findPostsByPostId(post_id).stream().findFirst().get().getTitle();
    }

    // slavare
    public void savePost(PostQueueMessage postQueueMessage){
        Post post = new Post();
        post.setPostId(postQueueMessage.getPost_id());
        post.setTitle(postQueueMessage.getTitle());
        post.setCreatorId(postQueueMessage.getCreator_id());
        post.setMessageType(postQueueMessage.getMessageType());
        post.setTopicId(postQueueMessage.getTopic_id());
        post.setSeen(false);

        postRepository.save(post);
    }
}

