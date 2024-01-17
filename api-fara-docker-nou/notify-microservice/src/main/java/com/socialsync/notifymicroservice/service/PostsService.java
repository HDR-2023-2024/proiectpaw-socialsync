package com.socialsync.notifymicroservice.service;

import com.socialsync.notifymicroservice.pojo.Comment;
import com.socialsync.notifymicroservice.pojo.PersistentPost;
import com.socialsync.notifymicroservice.pojo.Post;
import com.socialsync.notifymicroservice.pojo.PostQueueMessage;
import com.socialsync.notifymicroservice.repositories.CommentRepository;
import com.socialsync.notifymicroservice.repositories.PersistentPostRepository;
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
    private PersistentPostRepository persistentPostRepository;

    // Notificarile nevazute ale unui user pentru postarile create de el
    public List<Post> getAllNewPostNotifUser (String user_id) {
        List<Post> userPosts = postRepository.findPostsByCreatorId(user_id);
        return userPosts;
    }

    // Delete la notificarile vazute
    public void deletePostNotif (String notif_id) {
        postRepository.deleteById(notif_id);
    }

    // slavare
    public void savePost(PostQueueMessage postQueueMessage){
        Post post = new Post();
        post.setPostId(postQueueMessage.getPost_id());
        post.setTitle(postQueueMessage.getTitle());
        post.setCreatorId(postQueueMessage.getCreator_id());
        post.setMessageType(postQueueMessage.getMessageType());
        post.setTopicId(postQueueMessage.getTopic_id());

        log.info("Post-id:" + postQueueMessage.getPost_id());

        // save in permanent repo too
        persistentPostRepository.save(new PersistentPost(post.getPostId(), post.getTitle(), post.getCreatorId()));
        postRepository.save(post);
    }
}

