package com.socialsync.querymicroservice.service;

import com.socialsync.querymicroservice.documents.CommentDocument;
import com.socialsync.querymicroservice.documents.PostDocument;
import com.socialsync.querymicroservice.documents.TopicDocument;
import com.socialsync.querymicroservice.documents.UserDocument;
import com.socialsync.querymicroservice.dto.CommentDTO;
import com.socialsync.querymicroservice.dto.PostSummaryDTO;
import com.socialsync.querymicroservice.dto.TopicSummaryDTO;
import com.socialsync.querymicroservice.dto.UserSummaryDTO;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class QueryService implements QueryServiceMethods {
    private CommentRepository commentRepository;

    private PostRepository postRepository;

    private UserRepository userRepository;

    private TopicRepository topicRepository;

    public List<UserSummaryDTO> fetchAllUsers(Integer page) {
        return userRepository.findAll(PageRequest.of(page, 10)).map(UserSummaryDTO::new).toList();
    }

    public Page<CommentDocument> fetchAllComments(Integer page) {
        return commentRepository.findAll(PageRequest.of(page, 10));
    }

    public List<CommentDTO> fetchPostComments(String id, Integer page) {
        try {
            return commentRepository.findAll().stream().filter(p -> p.getPostId().equals(id)).map(CommentDTO::new).toList().subList(page * 10, (page + 1) * 10);
        } catch (IndexOutOfBoundsException ex) {
            List<CommentDTO> list = commentRepository.findAll().stream().filter(p -> p.getPostId().equals(id)).map(CommentDTO::new).toList();
            return list.subList(Math.max(list.size() - 10, 0), Math.max(list.size() - 1, 0));
        }
    }

    public List<PostSummaryDTO> fetchAllPosts(Integer page) {
        return postRepository.findAll(PageRequest.of(page, 10)).map(PostSummaryDTO::new).toList();
    }

    public Optional<PostDocument> fetchPostById(String id) {
        return postRepository.findById(id);
    }

    public List<TopicSummaryDTO> fetchAllTopics(Integer page) {
        return topicRepository.findAll(PageRequest.of(page, 10)).map(TopicSummaryDTO::new).toList();
    }

    public Optional<TopicDocument> fetchTopic(String id) {
        return topicRepository.findById(id);
    }

    public List<PostSummaryDTO> fetchTopicPosts(String id, Integer page) {
        return postRepository.findAllByTopicId(id, PageRequest.of(page, 10)).map(PostSummaryDTO::new).toList();
    }

    public List<TopicSummaryDTO> searchTopicsByName(String query, Integer page) {
        return topicRepository.searchByName(query + "*", PageRequest.of(page, 10))
                .map(TopicSummaryDTO::new).toList();
    }

    public Optional<UserDocument> fetchUser(String id) {
        return userRepository.findById(id);
    }

    public List<UserSummaryDTO> searchUsersByUsername(String query, Integer page) {
        return userRepository.searchByUsername(query + "*", PageRequest.of(page, 10))
                .map(UserSummaryDTO::new).toList();
    }
    @Override
    public void handleComment(CommentQueueMessage msgQ) throws RuntimeException {
        log.info("Handling comment " + msgQ.getComment().getId());
        CommentDocument commentDocument = new CommentDocument(msgQ.getComment());

        boolean parentPostExists = postRepository.existsById(commentDocument.getPostId());
        Optional<UserDocument> creator = userRepository.findById(msgQ.getComment().getCreatorId());

        boolean override = creator.isEmpty() && !parentPostExists && (msgQ.getComment().getCreatorId().equals("-1") || msgQ.getComment().getPostId().equals("-1"));

        if (parentPostExists && creator.isPresent() || override) {
            commentDocument.setCreator(!override ? new UserSummaryDTO(creator.get()) : new UserSummaryDTO(findRandomUser()));
            if (override)
                commentDocument.setPostId(findRandomPostId());

            log.info("Post and creator found for comment " + commentDocument.getId());
            boolean commentExists = commentRepository.existsById(commentDocument.getId());
            switch (msgQ.getType()) {
                case CREATE -> {
                    if (!commentExists) {
                        log.info("Adding comment " + msgQ.getComment().getId());
                        commentRepository.save(commentDocument);
                    }
                    else {
                        throw new CommentException("CREATE: Comment " + commentDocument.getId() + " already exists!");
                    }
                }
                case UPDATE -> {
                    if (commentExists) {
                        log.info("Updating comment " + msgQ.getComment().getId());
                        commentRepository.save(commentDocument);
                    }
                    else {
                        commentRepository.save(commentDocument);
                        throw new CommentException("UPDATE: Comment " + commentDocument.getId() + " to be updated not found! Created instead!");
                    }
                }
                case DELETE -> {
                    if (commentExists) {
                        log.info("Deleting comment " + msgQ.getComment().getId());
                        commentRepository.delete(commentDocument);
                    }
                    else
                        throw new CommentException("DELETE: Comment " + commentDocument.getId() + " to be deleted not found!");

                }
            }
        } else
            throw new ParentException("Post " + commentDocument.getPostId() + " or creator " + msgQ.getComment().getCreatorId() + " not found for comment!");

    }

    @Override
    public void handlePost(PostQueueMessage msgQ) throws RuntimeException {
        log.info("Handling post " + msgQ.getPost().getId());
        PostDocument postDocument = new PostDocument(msgQ.getPost());
        Optional<UserDocument> creator = userRepository.findById(msgQ.getPost().getCreatorId());
        boolean parentTopicExists = topicRepository.existsById(postDocument.getTopicId());

        //boolean override = creator.isEmpty() && !parentTopicExists || (msgQ.getPost().getCreatorId().equals("-1") || msgQ.getPost().getTopicId().equals("-1"));
        boolean override = msgQ.getPost().getCreatorId().equals("-1") || msgQ.getPost().getTopicId().equals("-1");

        if (parentTopicExists && creator.isPresent() || override) {
            boolean postExists = postRepository.existsById(postDocument.getId());
            postDocument.setCreator(!override ? new UserSummaryDTO(creator.get()) : new UserSummaryDTO(findRandomUser()));
            if (override)
                postDocument.setTopicId(findRandomTopicId());

            switch (msgQ.getType()) {
                case CREATE -> {
                    if (!postExists) {
                        log.info("Adding post " + msgQ.getPost().getId());
                        postRepository.save(postDocument);
                    }
                    else
                        throw new PostException("CREATE: Post " + postDocument.getId() + " already exists!");
                }
                case UPDATE -> {
                    if (postExists) {
                        log.info("Updating post " + msgQ.getPost().getId());
                        postRepository.save(postDocument);
                    }
                    else {
                        postRepository.save(postDocument);
                        throw new PostException("UPDATE: Post " + postDocument.getId() + " does not exist! Created one instead!");
                    }
                }
                case DELETE -> {
                    if (postExists) {
                        log.info("Deleting post " + msgQ.getPost().getId());
                        postRepository.delete(postDocument);
                    }
                    else
                        throw new PostException("DELETE: Post " + postDocument.getId() + " to be deleted not found!");
                }
                case UPVOTE -> {
                    if (postExists) {
                        log.info("Post " + msgQ.getPost().getId() + " upvoted by " + postDocument.getCreator().getId());
                        PostDocument postInDB = postRepository.findById(msgQ.getPost().getId()).get();
                        postInDB.addUpvote(postDocument.getCreator().getId());
                        postRepository.save(postInDB);
                    }
                    else
                        throw new PostException("UPVOTE: Post " + postDocument.getId() + " to be upvoted not found");
                }
                case DOWNVOTE -> {
                    if (postExists) {
                        log.info("Post " + msgQ.getPost().getId() + " downvoted by " + postDocument.getCreator().getId());
                        PostDocument postInDB = postRepository.findById(msgQ.getPost().getId()).get();
                        postInDB.addDownvote(postDocument.getCreator().getId());
                        postRepository.save(postInDB);
                    }
                    else
                        throw new PostException("DOWNVOTE: Post " + postDocument.getId() + " to be downvoted not found");
                }
            }
        }
        else
            throw new ParentException("Topic " + msgQ.getPost().getTopicId() + " or creator " + msgQ.getPost().getCreatorId() + " not found for post!");
    }

    @Override
    public void handleTopic(TopicQueueMessage msgQ) throws RuntimeException {
        log.info("Handling topic " + msgQ.getTopic().getId());
        TopicDocument topicDocument = new TopicDocument(msgQ.getTopic());

        Optional<UserDocument> creator = userRepository.findById(msgQ.getTopic().getCreatorId());
        boolean override = creator.isEmpty() && Objects.equals(msgQ.getTopic().getCreatorId(), "-1");

        if (creator.isPresent() || override) {
            topicDocument.setCreator(!override ? new UserSummaryDTO(creator.get()) : new UserSummaryDTO(findRandomUser()));
            boolean topicExists = topicRepository.existsById(topicDocument.getId());
            switch (msgQ.getType()) {
                case CREATE -> {
                    if (!topicExists) {
                        log.info("Adding topic " + msgQ.getTopic().getId());
                        topicRepository.save(topicDocument);
                    }
                    else
                        throw new TopicException("CREATE: Topic " + topicDocument.getId()  + " already exists!");
                }
                case UPDATE -> {
                    if (topicExists) {
                        log.info("Updating topic " + msgQ.getTopic().getId());
                        topicRepository.save(topicDocument);
                    }
                    else {
                        topicRepository.save(topicDocument);
                        throw new TopicException("UPDATE: Topic " + topicDocument.getId() + " does not exist! Created one instead!");
                    }
                }
                case DELETE -> {
                    if (topicExists) {
                        log.info("Deleting topic " + msgQ.getTopic().getId());
                        topicRepository.delete(topicDocument);
                    }
                    else
                        throw new TopicException("DELETE: Topic " + topicDocument.getId() + " to be deleted not found!");
                }
            }
        }
        else
            throw new ParentException("Creator " + msgQ.getTopic().getCreatorId() + " does not exists!");
    }

    @Override
    public void handleUser(UserQueueMessage msgQ) throws RuntimeException {
        log.info("Handling user " + msgQ.getUser().getId());
        UserDocument user = new UserDocument(msgQ.getUser());

        boolean userExists = userRepository.existsById(user.getId());
        switch (msgQ.getType()) {
            case CREATE -> {
                if (!userExists) {
                    log.info("Adding user " + msgQ.getUser().getId());
                    userRepository.save(user);
                }
                else
                    throw new UserException("CREATE: User " + user.getId() + " already exists!");
            }
            case UPDATE -> {
                if (userExists) {
                    log.info("Updating user " + msgQ.getUser().getId());
                    userRepository.save(user);
                }
                else {
                    userRepository.save(user);
                    throw new UserException("UPDATE: User " + user.getId() + " does not exist! Created one instead!");
                }
            }
            case DELETE -> {
                if (userExists) {
                    log.info("Deleting user " + msgQ.getUser().getId());
                    userRepository.delete(user);
                }
                else
                    throw new UserException("DELETE: User " + user.getId() + " does not exist!");
            }
        }
    }

    private UserDocument findRandomUser() {
        List<UserDocument> firstPage = userRepository.findAll(PageRequest.of(0, 10)).getContent();
        return firstPage.get(new Random().nextInt(firstPage.size()));
    }

    private String findRandomTopicId() {
        List<TopicDocument> firstPage = topicRepository.findAll(PageRequest.of(0, 10)).getContent();
        return firstPage.get(new Random().nextInt(firstPage.size())).getId();
    }

    private String findRandomPostId() {
        List<PostDocument> firstPage = postRepository.findAll(PageRequest.of(0, 10)).getContent();
        return firstPage.get(new Random().nextInt(firstPage.size())).getId();
    }
}
