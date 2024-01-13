package com.socialsync.querymicroservice.service;

import com.socialsync.querymicroservice.documents.CommentDocument;
import com.socialsync.querymicroservice.documents.PostDocument;
import com.socialsync.querymicroservice.documents.TopicDocument;
import com.socialsync.querymicroservice.documents.UserDocument;
import com.socialsync.querymicroservice.dto.*;
import com.socialsync.querymicroservice.interfaces.QueryServiceMethods;
import com.socialsync.querymicroservice.pojo.CommentQueueMessage;
import com.socialsync.querymicroservice.pojo.PostQueueMessage;
import com.socialsync.querymicroservice.pojo.TopicQueueMessage;
import com.socialsync.querymicroservice.pojo.UserQueueMessage;
import com.socialsync.querymicroservice.pojo.enums.Categorie;
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
import java.util.Optional;

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
            return commentRepository
                    .findAll()
                    .stream()
                    .filter(p -> p.getPostId().equals(id)).map(CommentDTO::new)
                    .toList()
                    .subList(page * 10, (page + 1) * 10);
        } catch (IndexOutOfBoundsException ex) {
            List<CommentDTO> list = commentRepository
                    .findAll()
                    .stream()
                    .filter(p -> p.getPostId().equals(id))
                    .map(CommentDTO::new).toList();
            return list.subList(page == 0 ? page : Math.max(page - 10, 0), list.size());
        }
    }

    public List<PostSummaryDTO> fetchAllPosts(Integer page, Optional<String> userId) {
        return postRepository.findAll(PageRequest.of(page, 10)).map(postDocument -> toSummary(postDocument, userId)).toList();
    }

    private PostSummaryDTO toSummary(PostDocument postDocument, Optional<String> userId) {
        PostSummaryDTO postSummaryDTO = new PostSummaryDTO(
                postDocument,
                new PostTopicSummaryDTO(
                        topicRepository
                                .findById(postDocument.getTopicId())
                                .orElseThrow(() -> new TopicException("Topic not found for post summary " + postDocument.getId()))));
        if (userId.isPresent()) {
            if (postDocument.getUpvotes().contains(userId.get()))
                postSummaryDTO.setLikedByUser(true);
            if (postDocument.getDownvotes().contains(userId.get()))
                postSummaryDTO.setDislikedByUser(true);
        }

        return postSummaryDTO;
    }

    public Optional<PostDocument> fetchPostById(String id) {
        return postRepository.findById(id);
    }

    public List<PostSummaryDTO> searchPostByTitle(String query, Integer page, Optional<String> userId) {
        return postRepository.searchByTitle(query + "*", PageRequest.of(page, 10))
                .map(postDocument -> toSummary(postDocument, userId)).toList();
    }

    public List<TopicSummaryDTO> fetchAllTopics(Integer page) {
        return topicRepository.findAll(PageRequest.of(page, 10)).map(TopicSummaryDTO::new).toList();
    }

    public Optional<TopicDocument> fetchTopic(String id) {
        return topicRepository.findById(id);
    }

    public List<TopicSummaryDTO> fetchTopicsByCategorie(Categorie categorie, Integer page) {
        log.info("Pagina " + page);
        log.info("Categorie " + categorie);
        return topicRepository.findAll().stream().map(TopicSummaryDTO::new).filter( topicSummaryDTO -> topicSummaryDTO.getCategorie().equals(categorie)).toList();
    }

    public List<PostSummaryDTO> fetchTopicPosts(String id, Integer page, Optional<String> userId) {
        return postRepository.findAllByTopicId(id, PageRequest.of(page, 10)).map(postDocument -> toSummary(postDocument, userId)).toList();
    }

    public List<TopicSummaryDTO> searchTopicsByName(String query, Integer page) {
        return topicRepository.searchByName(query + "*", PageRequest.of(page, 10))
                .map(TopicSummaryDTO::new).toList();
    }

    public List<TopicSummaryDTO> fetchTopicsByCreatorId(String id, Integer page) {
        try {
            return topicRepository
                    .findAll()
                    .stream()
                    .filter(p -> p.getCreator().getId().equals(id))
                    .map(TopicSummaryDTO::new)
                    .toList()
                    .subList(page * 10, (page + 1) * 10);
        } catch (IndexOutOfBoundsException ex) {
            List<TopicSummaryDTO> list = topicRepository
                    .findAll()
                    .stream()
                    .filter(p -> p.getCreator().getId().equals(id))
                    .map(TopicSummaryDTO::new)
                    .toList();
            return list.subList(page == 0 ? page : Math.max(page - 10, 0), list.size());
        }
    }

    public List<PostSummaryDTO> fetchPostsByCreatorId(String id, Integer page) {
        try {
            return postRepository
                    .findAll()
                    .stream()
                    .filter(p -> p.getCreator().getId().equals(id))
                    .map(postDocument -> toSummary(postDocument, Optional.of(id)))
                    .toList()
                    .subList(page * 10, (page + 1) * 10);
        } catch (IndexOutOfBoundsException ex) {
            List<PostSummaryDTO> list = postRepository
                    .findAll()
                    .stream()
                    .filter(p -> p.getCreator().getId().equals(id))
                    .map(postDocument -> toSummary(postDocument, Optional.of(id)))
                    .toList();
            return list.subList(page == 0 ? page : Math.max(page - 10, 0), list.size());
        }
    }

    public List<CommentDTO> fetchCommentsByCreatorId(String id, Integer page) {
        try {
            return commentRepository
                    .findAll()
                    .stream()
                    .filter(p -> p.getCreator().getId().equals(id))
                    .map(CommentDTO::new)
                    .toList()
                    .subList(page * 10, (page + 1) * 10);
        } catch (IndexOutOfBoundsException ex) {
            List<CommentDTO> list = commentRepository
                    .findAll()
                    .stream()
                    .filter(p -> p.getCreator().getId().equals(id))
                    .map(CommentDTO::new)
                    .toList();
            return list.subList(page == 0 ? page : Math.max(page - 10, 0), list.size());
        }
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


        if (parentPostExists && creator.isPresent()) {
            log.info("Post and creator found for comment " + commentDocument.getId());
            commentDocument.setCreator(new UserSummaryDTO(creator.get()));

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
        log.info(String.valueOf(postDocument));
        log.info(msgQ.getPost().getTopicId());
        log.info(msgQ.getPost().getCreatorId());
        boolean parentTopicExists = topicRepository.existsById(msgQ.getPost().getTopicId());
        Optional<UserDocument> creator = userRepository.findById(msgQ.getPost().getCreatorId());


        if (parentTopicExists && creator.isPresent()) {
            postDocument.setCreator(new UserSummaryDTO(creator.get()));
            Optional<PostDocument> post = postRepository.findById(postDocument.getId());

            switch (msgQ.getType()) {
                case CREATE -> {
                    if (post.isEmpty()) {
                        log.info("Adding post " + msgQ.getPost().getId());
                        postRepository.save(postDocument);
                    }
                    else
                        throw new PostException("CREATE: Post " + postDocument.getId() + " already exists!");
                }
                case UPDATE -> {
                    if (post.isPresent()) {
                        log.info("Updating post " + msgQ.getPost().getId());
                        postRepository.save(postDocument);
                    }
                    else {
                        postRepository.save(postDocument);
                        throw new PostException("UPDATE: Post " + postDocument.getId() + " does not exist! Created one instead!");
                    }
                }
                case DELETE -> {
                    if (post.isPresent()) {
                        log.info("Deleting post " + msgQ.getPost().getId());
                        postRepository.delete(postDocument);
                    }
                    else
                        throw new PostException("DELETE: Post " + postDocument.getId() + " to be deleted not found!");
                }
                case UPVOTE -> {
                    if (post.isPresent()) {
                        log.info("Post " + msgQ.getPost().getId() + " upvoted by " + postDocument.getCreator().getId());
                        PostDocument postInDB = post.get();
                        postInDB.addUpvote(postDocument.getCreator().getId());
                        postRepository.save(postInDB);
                    }
                    else
                        throw new PostException("UPVOTE: Post " + postDocument.getId() + " to be upvoted not found");
                }
                case DOWNVOTE -> {
                    if (post.isPresent()) {
                        log.info("Post " + msgQ.getPost().getId() + " downvoted by " + postDocument.getCreator().getId());
                        PostDocument postInDB = post.get();
                        postInDB.addDownvote(postDocument.getCreator().getId());
                        postRepository.save(postInDB);
                    }
                    else
                        throw new PostException("DOWNVOTE: Post " + postDocument.getId() + " to be downvoted not found");
                }
            }
        }
        else
            throw new ParentException("Topic " + msgQ.getPost().getTopicId() + " or creator " + msgQ.getPost().getCreatorId() + " not found for post! Topic exists: " + parentTopicExists + "Creator exists: " + creator.isPresent());
    }

    @Override
    public void handleTopic(TopicQueueMessage msgQ) throws RuntimeException {
        log.info("Handling topic " + msgQ.getTopic().getId());
        TopicDocument topicDocument = new TopicDocument(msgQ.getTopic());
        Optional<UserDocument> creator = userRepository.findById(msgQ.getTopic().getCreatorId());

        if (creator.isPresent()) {
            topicDocument.setCreator(new UserSummaryDTO(creator.get()));
            Optional<TopicDocument> topic = topicRepository.findById(topicDocument.getId());
            switch (msgQ.getType()) {
                case CREATE -> {
                    if (topic.isEmpty()) {
                        log.info("Adding topic " + msgQ.getTopic().getId());
                        topicRepository.save(topicDocument);
                    }
                    else
                        throw new TopicException("CREATE: Topic " + topicDocument.getId()  + " already exists!");
                }
                case UPDATE -> {
                    if (topic.isPresent()) {
                        log.info("Updating topic " + msgQ.getTopic().getId());
                        topicRepository.save(topicDocument);
                    }
                    else {
                        topicRepository.save(topicDocument);
                        throw new TopicException("UPDATE: Topic " + topicDocument.getId() + " does not exist! Created one instead!");
                    }
                }
                case DELETE -> {
                    if (topic.isPresent()) {
                        log.info("Deleting topic " + msgQ.getTopic().getId());
                        topicRepository.delete(topicDocument);
                    }
                    else
                        throw new TopicException("DELETE: Topic " + topicDocument.getId() + " to be deleted not found!");
                }
                case JOIN -> {
                    if (topic.isPresent()) {
                        log.info("User " + msgQ.getTopic().getCreatorId() + " joined " + topicDocument.getId());
                        topic.get().getMembers().add(topicDocument.getCreator());
                        topicRepository.save(topic.get());
                    }
                    else
                        throw new TopicException("JOIN: Topic " + topicDocument.getId() + "doest not exist");
                }
                case LEAVE -> {
                    if (topic.isPresent()) {
                        log.info("User " + msgQ.getTopic().getCreatorId() + " joined " + topicDocument.getId());
                        topic.get().getMembers().remove(topicDocument.getCreator());
                        topicRepository.save(topic.get());
                    }
                    else
                        throw new TopicException("LEAVE: Topic " + topicDocument.getId() + "doest not exist");
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

//    private UserDocument findRandomUser() {
//        List<UserDocument> firstPage = userRepository.findAll(PageRequest.of(0, 10)).getContent();
//        return firstPage.get(new Random().nextInt(firstPage.size()));
//    }
//
//    private String findRandomTopicId() {
//        List<TopicDocument> firstPage = topicRepository.findAll(PageRequest.of(0, 10)).getContent();
//        return firstPage.get(new Random().nextInt(firstPage.size())).getId();
//    }
//
//    private String findRandomPostId() {
//        List<PostDocument> firstPage = postRepository.findAll(PageRequest.of(0, 10)).getContent();
//        return firstPage.get(new Random().nextInt(firstPage.size())).getId();
//    }
}
