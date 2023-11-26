package com.socialsync.querymicroservice.service;

import com.socialsync.querymicroservice.dto.CommentDTO;
import com.socialsync.querymicroservice.dto.PostDTO;
import com.socialsync.querymicroservice.dto.TopicDTO;
import com.socialsync.querymicroservice.dto.UserDTO;
import com.socialsync.querymicroservice.interfaces.QueryServiceMethods;
import com.socialsync.querymicroservice.pojo.CommentQueueMessage;
import com.socialsync.querymicroservice.pojo.PostQueueMessage;
import com.socialsync.querymicroservice.pojo.TopicQueueMessage;
import com.socialsync.querymicroservice.pojo.UserQueueMessage;
import com.socialsync.querymicroservice.repository.CommentRepository;
import com.socialsync.querymicroservice.repository.PostRepository;
import com.socialsync.querymicroservice.repository.TopicRepository;
import com.socialsync.querymicroservice.repository.UserRepository;
import com.socialsync.querymicroservice.util.exceptions.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.exceptions.JedisDataException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class QueryQueryService implements QueryServiceMethods {
    private CommentRepository commentRepository;

    private PostRepository postRepository;

    private UserRepository userRepository;

    private TopicRepository topicRepository;

    @Override
    public Page<UserDTO> fetchAllUsers(Integer page) {
        return userRepository.findAll(PageRequest.of(page, 10));
    }

    @Override
    public Page<CommentDTO> fetchAllComments(Integer page) {
        return commentRepository.findAll(PageRequest.of(page, 10));
    }

    @Override
    public Page<PostDTO> fetchAllPosts(Integer page) {
        return postRepository.findAll(PageRequest.of(page, 10));
    }

    @Override
    public Page<TopicDTO> fetchAllTopics(Integer page) {
        return topicRepository.findAll(PageRequest.of(page, 10));
    }

    @Override
    public Page<UserDTO> fetchAllUsersByUsername(String username, Integer page) {
        try {
            return userRepository.searchByUsername(username + "*", PageRequest.of(page, 10));
        } catch (JedisDataException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public Page<PostDTO> fetchAllPostByTopicId(Integer page, String topicId) {
        return postRepository.findAllByTopicId(PageRequest.of(page, 10), topicId);
    }

    @Override
    public Page<CommentDTO> fetchAllCommentsByPostId(Integer page, String postId) {
        return commentRepository.findAllByPostId(PageRequest.of(page, 10), postId);
    }

    @Override
    public List<TopicDTO> fetchAllTopicsByName(String topicName) {
        return topicRepository.searchByName(topicName);
    }

    @Override
    public Page<CommentDTO> fetchAllCommentsByUserId(Integer page, String id) {
        return commentRepository.findAllByCreator_Id(PageRequest.of(page, 10), id);
    }

    @Override
    public Page<PostDTO> fetchAllPostByUserId(Integer page, String id) {
        return postRepository.findAllByCreator_Id(PageRequest.of(page, 10), id);
    }


    @Override
    public void handleComment(CommentQueueMessage msgQ) throws RuntimeException {
        log.info("Handling comment " + msgQ.getComment().getId());
        CommentDTO commentDTO = new CommentDTO(msgQ.getComment());

        boolean parentPostExists = postRepository.existsById(commentDTO.getPostId());
        Optional<UserDTO> creator = userRepository.findById(msgQ.getComment().getCreatorId());

        if (parentPostExists && creator.isPresent()) {
            commentDTO.setCreator(creator.get());
            log.info("Post and creator found for comment " + commentDTO.getId());
            boolean commentExists = commentRepository.existsById(commentDTO.getId());
            switch (msgQ.getType()) {
                case CREATE -> {
                    if (!commentExists) {
                        log.info("Adding comment " + msgQ.getComment().getId());
                        commentRepository.save(commentDTO);
                    }
                    else {
                        throw new CommentException("Comment " + commentDTO.getId() + " already exists!");
                    }
                }
                case UPDATE -> {
                    if (commentExists) {
                        log.info("Updating comment " + msgQ.getComment().getId());
                        commentRepository.save(commentDTO);
                    }
                    else {
                        commentRepository.save(commentDTO);
                        throw new CommentException("Comment " + commentDTO.getId() + " to be updated not found! Created instead!");
                    }
                }
                case DELETE -> {
                    if (commentExists) {
                        log.info("Deleting comment " + msgQ.getComment().getId());
                        commentRepository.delete(commentDTO);
                    }
                    else
                        throw new CommentException("Comment " + commentDTO.getId() + " to be deleted not found!");

                }
            }
        } else
            throw new ParentException("Post " + commentDTO.getPostId() + " or creator " + msgQ.getComment().getCreatorId() + " not found for comment!");

    }

    @Override
    public void handlePost(PostQueueMessage msgQ) throws RuntimeException {
        log.info("Handling post " + msgQ.getPost().getId());
        PostDTO postDTO = new PostDTO(msgQ.getPost());
        Optional<UserDTO> creator = userRepository.findById(msgQ.getPost().getCreatorId());
        boolean parentTopicExists = topicRepository.existsById(postDTO.getTopicId());

        if (parentTopicExists && creator.isPresent()) {
            boolean postExists = postRepository.existsById(postDTO.getId());
            postDTO.setCreator(creator.get());
            switch (msgQ.getType()) {
                case CREATE -> {
                    if (!postExists) {
                        log.info("Adding post " + msgQ.getPost().getId());
                        postRepository.save(postDTO);
                    }
                    else
                        throw new PostException("Post " + postDTO.getId() + " already exists!");
                }
                case UPDATE -> {
                    if (postExists) {
                        log.info("Updating post " + msgQ.getPost().getId());
                        postRepository.save(postDTO);
                    }
                    else {
                        postRepository.save(postDTO);
                        throw new PostException("Post " + postDTO.getId() + " does not exist! Created one instead!");
                    }
                }
                case DELETE -> {
                    if (postExists) {
                        log.info("Deleting post " + msgQ.getPost().getId());
                        postRepository.delete(postDTO);
                    }
                    else
                        throw new PostException("Post " + postDTO.getId() + " to be deleted not found!");
                }
            }
        }
        else
            throw new ParentException("Topic " + msgQ.getPost().getTopicId() + " or creator " + msgQ.getPost().getCreatorId() + " not found for comment!");
    }

    @Override
    public void handleTopic(TopicQueueMessage msgQ) throws RuntimeException {
        log.info("Handling topic " + msgQ.getTopic().getId());
        TopicDTO topicDTO = new TopicDTO(msgQ.getTopic());

        Optional<UserDTO> creator = userRepository.findById(msgQ.getTopic().getCreatorId());

        if (creator.isPresent()) {
            topicDTO.setCreatorId(creator.get());
            boolean topicExists = topicRepository.existsById(topicDTO.getId());
            switch (msgQ.getType()) {
                case CREATE -> {
                    if (!topicExists) {
                        log.info("Adding topic " + msgQ.getTopic().getId());
                        topicRepository.save(topicDTO);
                    }
                    else
                        throw new TopicException("Topic " + topicDTO.getId()  + " already exists!");
                }
                case UPDATE -> {
                    if (topicExists) {
                        log.info("Updating topic " + msgQ.getTopic().getId());
                        topicRepository.save(topicDTO);
                    }
                    else {
                        topicRepository.save(topicDTO);
                        throw new TopicException("Topic " + topicDTO.getId() + " does not exist! Created one instead!");
                    }
                }
                case DELETE -> {
                    if (topicExists) {
                        log.info("Deleting topic " + msgQ.getTopic().getId());
                        topicRepository.delete(topicDTO);
                    }
                    else
                        throw new TopicException("Topic " + topicDTO.getId() + " to be deleted not found!");
                }
            }
        }
        else
            throw new ParentException("Creator " + msgQ.getTopic().getCreatorId() + " does not exists!");
    }

    @Override
    public void handleUser(UserQueueMessage msgQ) throws RuntimeException {
        log.info("Handling user " + msgQ.getUser().getId());
        UserDTO user = new UserDTO(msgQ.getUser());

        boolean userExists = userRepository.existsById(user.getId());
        switch (msgQ.getType()) {
            case CREATE -> {
                if (!userExists) {
                    log.info("Adding user " + msgQ.getUser().getId());
                    userRepository.save(user);
                }
                else
                    throw new UserException("User " + user.getId() + " already exists!");
            }
            case UPDATE -> {
                if (userExists) {
                    log.info("Updating user " + msgQ.getUser().getId());
                    userRepository.save(user);
                }
                else {
                    userRepository.save(user);
                    throw new UserException("User " + user.getId() + " does not exist! Created one instead!");
                }
            }
            case DELETE -> {
                if (userExists) {
                    log.info("Deleting user " + msgQ.getUser().getId());
                    userRepository.delete(user);
                }
                else
                    throw new UserException("User " + user.getId() + " does not exist!");
            }
        }
    }
}
