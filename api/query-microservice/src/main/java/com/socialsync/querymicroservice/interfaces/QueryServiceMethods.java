package com.socialsync.querymicroservice.interfaces;

import com.socialsync.querymicroservice.dto.CommentDTO;
import com.socialsync.querymicroservice.dto.PostDTO;
import com.socialsync.querymicroservice.dto.TopicDTO;
import com.socialsync.querymicroservice.dto.UserDTO;
import com.socialsync.querymicroservice.pojo.CommentQueueMessage;
import com.socialsync.querymicroservice.pojo.PostQueueMessage;
import com.socialsync.querymicroservice.pojo.TopicQueueMessage;
import com.socialsync.querymicroservice.pojo.UserQueueMessage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QueryServiceMethods {
    void handleTopic(TopicQueueMessage msgQ);
    void handlePost(PostQueueMessage msgQ);
    void handleComment(CommentQueueMessage msgQ);
    void handleUser(UserQueueMessage msgQ);
    Page<UserDTO> fetchAllUsers(Integer page);
    Page<CommentDTO> fetchAllComments(Integer page);
    Page<PostDTO> fetchAllPosts(Integer page);
    Page<TopicDTO> fetchAllTopics(Integer page);

    Page<UserDTO> fetchAllUsersByUsername(String username, Integer page);
    Page<PostDTO> fetchAllPostByTopicId(Integer page, String topicId);
    Page<CommentDTO> fetchAllCommentsByPostId(Integer page, String postId);
    Page<TopicDTO> fetchAllTopicsByName(String topicName, Integer page);
    Page<CommentDTO> fetchAllCommentsByUserId(Integer page, String id);
    Page<PostDTO> fetchAllPostByUserId(Integer page, String id);
}
