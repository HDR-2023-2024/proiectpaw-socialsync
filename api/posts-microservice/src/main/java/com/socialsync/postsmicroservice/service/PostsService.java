package com.socialsync.postsmicroservice.service;

import com.socialsync.postsmicroservice.interfaces.PostRepository;
import com.socialsync.postsmicroservice.interfaces.PostsServiceMethods;
import com.socialsync.postsmicroservice.pojo.Post;
import com.socialsync.postsmicroservice.util.exceptions.PostNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Service
public class PostsService implements PostsServiceMethods {

    private PostRepository repository;

    @Override
    public HashMap<String, Post> fetchAllPosts() {
        HashMap<String, Post> lista = new HashMap<>();

        List<Post> posts =  repository.findAll();

        for (Post post : posts) {
            lista.put(post.getId(), post);
        }

        return lista;
    }

    @Override
    public Post fetchPostById(String id) throws PostNotFound {
        return repository.findById(id).orElseThrow(() -> new PostNotFound("Not found: " + id));
    }

    @Override
    public void addPost(Post post)  {
        post.setTimestampCreated(Instant.now().getEpochSecond());
        repository.insert(post);
    }

    @Override
    public void updatePost(String id, Post post) throws PostNotFound {
        repository.findById(id).map(elem -> {
                elem.setContent(post.getContent());
                elem.setTitle(post.getTitle());
                elem.setCreatorId(post.getCreatorId());
                elem.setTopicId(post.getTopicId());
                elem.setTimestampUpdated(Instant.now().getEpochSecond());
                repository.save(elem);
                return elem;
        }).orElseThrow(() -> {
            repository.save(post);
            return new PostNotFound("Post not found. Created one instead.");
        });
    }

    @Override
    public void deletePost(String id) {
        repository.deleteById(id);
    }
}
