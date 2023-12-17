package com.socialsync.postsmicroservice.service;

import com.google.gson.Gson;
import com.socialsync.postsmicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.postsmicroservice.pojo.PostQueueMessage;
import com.socialsync.postsmicroservice.pojo.enums.QueueMessageType;
import com.socialsync.postsmicroservice.repository.PostRepository;
import com.socialsync.postsmicroservice.interfaces.PostsServiceMethods;
import com.socialsync.postsmicroservice.pojo.Post;
import com.socialsync.postsmicroservice.util.exceptions.PostNotFound;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@AllArgsConstructor
@Service
@Slf4j
@EnableScheduling
public class PostsService implements PostsServiceMethods {

    private PostRepository repository;

    private RabbitMqConnectionFactoryComponent conectionFactory;

    private AmqpTemplate amqpTemplate;

    private Gson gson;

    @Bean
    void initTemplate() {
        this.amqpTemplate = conectionFactory.rabbitTemplate();
    }

    private void sendMessage(PostQueueMessage post) {
        String json = gson.toJson(post);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKey(), json);
    }

    void deleteEverything() {
        repository.findAll().forEach(x -> {
            try {
                deletePost(x.getId());
            } catch (PostNotFound e) {
                throw new RuntimeException(e);
            }
        });
    }

    static List<String> titluPostare = List.of(
            "Orașul Ascuns",
            "Luminile Noptii",
            "Portale în Alt Univers",
            "Misterul Trecutului",
            "Călătorie Fără Destinație",
            "Pe Următoarea Stea",
            "În Căutarea Sensului",
            "Paradisul Pierdut",
            "În Umbra Realității",
            "Poveștile Stelare",
            "Labirintul Minții",
            "Destinul Ascuns",
            "Până la Marginea Lumii",
            "Culoarea Vântului",
            "Sufletul Cosmosului",
            "Prizonier în Timp",
            "Scriind Pe Filele Destinului",
            "Rădăcinile Invisibile",
            "Zborul Spre Necunoscut",
            "Frumusețea Imaterială",
            "Descoperind Culorile Sufletului",
            "Călătorie Prin Nebuloase",
            "Dincolo de Orizont",
            "Portretul Inimii",
            "În Căutarea Adevărului",
            "În Vâltoarea Eternității",
            "Când Steltele Plâng",
            "Labirintul Timpului",
            "Căutătorul de Răspunsuri",
            "Povestea Fără Sfârșit",
            "În Jocul Cuantelor",
            "Călătorind Pe Râuri de Lumină",
            "Intrând în Tărâmul Viselor",
            "Oglinda Dincolo de Realitate",
            "Legături Neexplorate",
            "Povestea Uitată",
            "Magia Nopții Albastre",
            "Răsăritul Lumii Noi",
            "Universul Într-o Picătură de Rouă",
            "Explorând Misterul",
            "În Căutarea Cunoașterii",
            "Sub Semnul Stelelor",
            "Aventuri În Dimensiuni Paralele",
            "Călătorind Prin Portale",
            "Scriind Pe Pergamentul Destinului",
            "Zbor deasupra Norilor",
            "Trăind În Vis",
            "Misterul Pădurei Întunecate",
            "Căutând Lumina Dincolo de Umbră",
            "Rătăcind în Labyrinthul Timpului",
            "Călătorind Cu Vântul",
            "În Căutarea Armoniei",
            "Pe Drumul Spre Infinit",
            "În Spatele Oglindelor",
            "Frumusețea Efemeră",
            "Lumea Dincolo de Vise",
            "Căutând Comori Îngropate",
            "Sufletul Peregrin",
            "Destinul în Carte Scrie",
            "În Căutarea Sufletului Pierdut",
            "Pe Drumul Norilor",
            "Sămânța Magiei",
            "Orașul Adormit",
            "Sufletul Călător",
            "Răsăritul Fără Apus",
            "Calea Fără Sfârșit",
            "Fereastra Către Infinit",
            "În Pădurea cu Zâne",
            "Scriind În Caietele Sufletului",
            "Descoperind Pășunile Eterne",
            "În Căutarea Inimii Pierdute",
            "Aroma Stelelor",
            "Călătorind Cu Luna",
            "Urmărind Cometele",
            "În Valurile Timpului",
            "Portretul Cuvintelor",
            "Ochiul Vârtejurilor",
            "Sufletul Fugind de Realitate",
            "Printre Pagini de Istorii",
            "Călătorind Prin Galaxii",
            "Răsăritul Dincolo de Munte",
            "În Căutarea Pământului Promis",
            "Labirintul Viselor",
            "Sufletul Pustiit",
            "Pe Aripile Gândurilor",
            "Rătăcind Prin Universul Paralel",
            "Lumea În Culori de Argint",
            "Căutând Lumină în Întuneric",
            "În Căutarea Nopților Nesfârșite",
            "Misterul Căii Lactee",
            "În Labirintul Sufletului",
            "Călătorind În Vânt",
            "În Căutarea Cetății de Nisip",
            "Scriind Cu Stelele",
            "Misterul Oglindelor",
            "În Căutarea Soarelui de Mâine",
            "Călătorind Pe Marea Eternității",
            "Pe Drumul Neexplorat",
            "În Spatele Porților Închise",
            "Fereastra către Infinitul Interior",
            "Aventura În Țara Minunilor"
    );

    @Bean
    void populateDb() {
        deleteEverything();

        for (int i = 0;i < 100; i++) {
            String titlu = titluPostare.get(new Random().nextInt(titluPostare.size()));
            String continut = "Acesta este un conținut scurt pentru postarea cu titlul \"" + titlu + "\".";
            Post post = new Post("-1", "-1", titlu, continut);
            addPost(post);
        }
    }

    @Bean
    @Scheduled(fixedDelay = 5000L)
    void newRandomPost() {
        log.info("We have " + repository.findAll().size() + " posts");

//        if (repository.findAll().size() > 200)
//            deleteEverything();

        String titlu = titluPostare.get(new Random().nextInt(titluPostare.size()));
        String continut = "Acesta este un conținut scurt pentru postarea cu titlul \"" + titlu + "\".";
        Post post = new Post("-1", "-1", titlu, continut);
        addPost(post);
    }

    @Bean
    @Scheduled(initialDelay = 1000L,fixedDelay = 1000)
    @SneakyThrows
    void randomLikeDislike() {
        boolean like = Math.random() < 0.5;
        Post randomPost = repository.findAll().get(new Random().nextInt(0, 100));

        if (like)
        {
            log.info("Post " + randomPost.getId() + " was upvoted");
            upvotePost(randomPost.getId(), "-1");
            upvotePost(randomPost.getId(), "-1");
            upvotePost(randomPost.getId(), "-1");
            upvotePost(randomPost.getId(), "-1");
            upvotePost(randomPost.getId(), "-1");
        }
        else
        {
            log.info("Post " + randomPost.getId() + " was downvoted");
            downvotePost(randomPost.getId(), "-1");
            downvotePost(randomPost.getId(), "-1");
            downvotePost(randomPost.getId(), "-1");
            downvotePost(randomPost.getId(), "-1");
            downvotePost(randomPost.getId(), "-1");
            downvotePost(randomPost.getId(), "-1");
        }
    }

    @Override
    public HashMap<String, Post> fetchAllPosts() {
        HashMap<String, Post> lista = new HashMap<>();

        List<Post> posts =  repository.findAll();

        for (Post post : posts)
            lista.put(post.getId(), post);

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
        sendMessage(new PostQueueMessage(QueueMessageType.CREATE, post));
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
                sendMessage(new PostQueueMessage(QueueMessageType.UPDATE, post));
                return elem;
        }).orElseThrow(() -> {
            post.setTimestampCreated(Instant.now().getEpochSecond());
            repository.insert(post);
            sendMessage(new PostQueueMessage(QueueMessageType.CREATE, post));
            return new PostNotFound("Post not found. Created one instead.");
        });
    }

    @Override
    public void deletePost(String id) throws PostNotFound {
        Optional<Post> post = repository.findById(id);

        if (post.isPresent()) {
            repository.deleteById(id);
            sendMessage(new PostQueueMessage(QueueMessageType.DELETE, post.get()));
        }
        else
            throw new PostNotFound("Post not found.");
    }

    @Override
    public void upvotePost(String postId, String userId) throws PostNotFound {
        Optional<Post> post = repository.findById(postId);

        if (post.isPresent()) {
            post.get().getUpvotes().add(userId);
            post.get().getDownvotes().remove(userId);

            repository.save(post.get());

            sendMessage(new PostQueueMessage(QueueMessageType.UPVOTE, new Post(postId, userId, post.get().getTopicId())));
//            sendMessage(new PostQueueMessage(QueueMessageType.UPVOTE, new Post(postId, userId, "657ca7e2fb8e4725915e20c9")));
        }
        else
            throw new PostNotFound("Post not found.");
    }

    @Override
    public void downvotePost(String postId, String userId) throws PostNotFound {
        Optional<Post> post = repository.findById(postId);

        if (post.isPresent()) {
            post.get().getDownvotes().add(userId);
            post.get().getUpvotes().remove(userId);

            repository.save(post.get());

            sendMessage(new PostQueueMessage(QueueMessageType.DOWNVOTE, new Post(postId, userId, post.get().getTopicId())));
//            sendMessage(new PostQueueMessage(QueueMessageType.DOWNVOTE, new Post(postId, userId, "657ca7e2fb8e4725915e20c9")));
        }
        else
            throw new PostNotFound("Post not found.");
    }
}
