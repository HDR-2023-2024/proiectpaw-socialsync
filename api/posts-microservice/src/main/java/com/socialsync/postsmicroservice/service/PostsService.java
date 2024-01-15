package com.socialsync.postsmicroservice.service;

import com.google.gson.Gson;
import com.socialsync.postsmicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.postsmicroservice.interfaces.PostsServiceMethods;
import com.socialsync.postsmicroservice.pojo.*;
import com.socialsync.postsmicroservice.pojo.enums.QueueMessageType;
import com.socialsync.postsmicroservice.repository.PostRepository;
import com.socialsync.postsmicroservice.repository.TopicIdRepository;
import com.socialsync.postsmicroservice.repository.UserIdRepository;
import com.socialsync.postsmicroservice.util.exceptions.PostNotFound;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@AllArgsConstructor
@Service
@Slf4j
@EnableScheduling
public class PostsService implements PostsServiceMethods {

    private UserIdRepository userIdRepository;

    private TopicIdRepository topicIdRepository;

    private PostRepository repository;

    private RabbitMqConnectionFactoryComponent conectionFactory;

    private AmqpTemplate amqpTemplate;

    private Gson gson;

    @Bean
    void initTemplate() {
        this.amqpTemplate = conectionFactory.rabbitTemplate();
    }

    @RabbitListener(queues = "${socialsync.rabbitmq.queue.userId}")
    void userIdListener(String msg) {
        try {
            if (msg.startsWith("DEL")) {
                String id = msg.split("#")[1];
                log.info("Deleting user " + id);
                userIdRepository.deleteById(id);
            }
            else {
                log.info("New user " + msg);
                userIdRepository.save(new User(msg));
                populateDb();
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

    @RabbitListener(queues = "${socialsync.rabbitmq.queue.topicId}")
    void topicIdListener(String msg) {
        try {
            if (msg.startsWith("DEL")) {
                String id = msg.split("#")[1];
                log.info("Deleting topic " + id);
                topicIdRepository.deleteById(id);
            }
            else {
                log.info("New topic " + msg);
                topicIdRepository.save(new Topic(msg));
                populateDb();
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

    private void sendMessage(PostQueueMessage post) {
        String json = gson.toJson(post);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKeyPosts(), json);
    }

    private void sendMessageNotification(PostNotification post) {
        String json = gson.toJson(post);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKeyNotify(), json);
    }

    private void sendMessageImage(PhotoMessageDto photoMessageDto) {
        String json = gson.toJson(photoMessageDto);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKeyImages(), json);
    }

    private void sendId(String id) {
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKeyIds(), id);
    }

    @Bean
    void deleteEverything() {
        userIdRepository.deleteAll();
        topicIdRepository.deleteAll();
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

    private static List<String> content = List.of(
            "Morbi sit amet fermentum purus. Nam pharetra nibh ut erat tincidunt tempus. Morbi vehicula lacinia ex, consectetur dignissim leo consequat vestibulum. Fusce rhoncus eros sed tortor egestas dapibus. Interdum et malesuada fames ac ante ipsum primis in faucibus. Proin quis felis vel leo laoreet tempor. Sed non tellus dolor.\n" +
                    "\n" +
                    "Aliquam ac maximus quam. Sed sed varius enim. Sed porttitor cursus ligula, sed bibendum tortor accumsan ut. Duis dapibus eleifend ligula sit amet accumsan. Nullam luctus fringilla eros. Fusce posuere enim vel erat accumsan lobortis. Vestibulum semper sapien at erat sollicitudin, ut aliquam est iaculis. Fusce non rhoncus massa, non dictum quam. Morbi non libero posuere urna porttitor posuere. Donec elementum tempus nibh, eu tempor lectus finibus et.",
            "Morbi sit amet fermentum purus. Nam pharetra nibh ut erat tincidunt tempus. Morbi vehicula lacinia ex, consectetur dignissim leo consequat vestibulum. Fusce rhoncus eros sed tortor egestas dapibus. Interdum et malesuada fames ac ante ipsum primis in faucibus. Proin quis felis vel leo laoreet tempor. Sed non tellus dolor.\n" +
                    "\n" +
                    "Aliquam ac maximus quam. Sed sed varius enim. Sed porttitor cursus ligula, sed bibendum tortor accumsan ut. Duis dapibus eleifend ligula sit amet accumsan. Nullam luctus fringilla eros. Fusce posuere enim vel erat accumsan lobortis. Vestibulum semper sapien at erat sollicitudin, ut aliquam est iaculis. Fusce non rhoncus massa, non dictum quam. Morbi non libero posuere urna porttitor posuere. Donec elementum tempus nibh, eu tempor lectus finibus et.",
            "Joia mult așteptată a sosit. Vodă și doamna, plecând în străinătate, trebuie să se oprească douăzeci și cinci de minute\n" +
                    "în gara din marginea orășelului Z... De dimineață, peronul gării, decorat cu împletitură de brad, cu marca județului,\n" +
                    "stegulețe tricolore și covorul cel roșu al primăriei, este înțesat de lume garnizoana, garda civică, școalele, autoritățile,\n" +
                    "notabilii și cât public a mai putut încăpea.\n" +
                    "Directorul a plecat de la șapte de-acasă spre a lua, împreună cu primarul, cele din urmă dispozițiuni la fața locului.\n" +
                    "Nevasta lui a rămas să se gătească și să vie mai târziu la gară cu copiii și cu amicul. Amicul este un profesor foarte\n" +
                    "tânăr, care locuiește la directorul de un an de zile; el dă lecții la copii și redijează Sentinela Ordinii; e băiat bun și\n" +
                    "scrie minunat: se dă ca aproape pozitiv că tot el a scris proclamația.\n" +
                    "Acum tot e gata... Directorul se duce la o extremitate a peronului și-și aruncă privirile la mulțimea adunată până în\n" +
                    "cealaltă extremitate... Atunci îi clipesc de departe în minte cuvintele Drapelului, și-și mângâie favoritele cu\n" +
                    "mulțumire...",
            "Un individ purtând pe umeri o șubă largă, cu o căciulă mare pe ceafă, stă la masă în restaurantul Gării de Nord și bea\n" +
                    "ceai. Are un geamantan de pânză cu cercuri de tinichea alături pe un scaun, și pe masă un sac de piele neagră, prin\n" +
                    "mănușa căruia își ține petrecută bine mâna stângă până la încheietura brațului. După figură, după maniere și port,\n" +
                    "trebuie să fie vreun negustor, ori arendaș din provincie.\n" +
                    "Un domn bine îmbrăcat, blană cu guler de astrahan și căciuliță asemenea, bea cafea la altă masă, fumează și se uită\n" +
                    "din când în când către provincial, care nu ia seama de loc că-l observă cineva. În același timp, domnul trage cu ochii\n" +
                    "la ușă: așteaptă desigur pe cineva, și cu nerăbdare.\n" +
                    "Negustorul plătește ceaiul. Îndată, iată și hamalul vine să-i spună că trenul a tras la peron; îi ia geamantanul de pânză\n" +
                    "și vrea să-i ia și sacul de piele, ca să-l ușureze pe pasager; dar acesta își trage repede mâna cu sacul și refuză\n" +
                    "serviciul. Sacul nu e mare, dar trebuie să fie greu, fiindcă pasagerul, ridicându-se, trebuie să-l țină și de dedesubt cu\n" +
                    "mâna dreaptă. Hamalul pornește spre ușă; negustorul după el.",
            "Sunt în lume amatori de fel de fel de colecțiuni curioase — și multe am văzut, de valori incalculabile.\n" +
                    "În clasele primare, odinioară, cunoșteam un băiat care avea o colecție de peste cinci mii de nasturi; mai târziu, am\n" +
                    "admirat la un tânăr zeci de albumuri pline de fotografii de femei frumoase; apoi, la un bărbat, două colecțiuni: una,\n" +
                    "de cărți poștale ilustrate, și alta, de cărți de citit, strânse încet-încet, de pe la prieteni uituci, unele neilustrate.\n" +
                    "Acum, în urmă, am făcut cunoștința unui domn care posedă o colecție de scrisori anonime — cea mai prețioasă\n" +
                    "desigur din câte, felurite, mi-a fost dat să văd... De la soață pe ale soțului, de la părinți pe ale copiilor, de la soacră pe\n" +
                    "ale ginerelui, de la stăpâne pe ale slujnicelor — ori, viceversa; unele cumpărate, altele dăruite, altele subtilizate; în\n" +
                    "fine — de unde, cum, cu ce răbdare, cu câte sacrificii le-a adunat — numai el știe... Cunoscându-i slăbiciunea, i-am\n" +
                    "dus și eu o anonimă, primită zilele trecute, în care o veche prietină (indubitabil, e dumneaei), iscălind „câțiva\n" +
                    "admiratori de odinioară\", mă picnește, vorba de pe vremuri, la coada ișlicului... Posesorul colecției mi-a mulțumit;\n" +
                    "dar, deși l-a primit, n-a rămas, cum m-așteptam, destul de încântat de specimenul meu...",
            "Preastimată doamnă, nu toate mamele sunt devotate cum sunteți dv., ceea ce ar trebui să le fie tot ce e mai sacru. Și\n" +
                    "eu sunt tată de familie, vă-nțeleg și vă admir. De aceea, în interesul scumpei dv. copile, așa de castă, dar totuși orbită\n" +
                    "de un amor nenorocit, mă grăbesc a vă pune în vedere că acela căruia vreți să-i încredințați viitorul ei este un om\n" +
                    "pierdut. Pe lângă vițiul alcoolismului și al variației în ultimul grad, a fost și în spital și are și patima foițelor, care, în\n" +
                    "calitatea sa de mânuitor de bani publici, poate, desigur, îl va duce la ultima treaptă, pe banca infamiei, condamnat de\n" +
                    "justiție pentru atât de colosale delapidări, ca mulți alți nenorociți de această tristă speță. Mi-am împlinit misiunea în\n" +
                    "conștiință. Rămâne ca dv., ca mamă devotată, înainte de a face pasul fatal, să avizați cu perspicacitate. (iscălit) Un\n" +
                    "bun tată de familie.",
            "Tânărule, ești în vârsta frumoasă a iluziunilor, când inima se deschide ca o floare cu toată sinceritatea... Onest, activ,\n" +
                    "fără nici un vițiu, virtuos, apreciat de superiori, iubit de inferiori, viitorul îți surâde. Nu mai poți trăi singur; dorești,\n" +
                    "după ce ți-ai cucerit o frumoasă situație prin propriile merite, ca fiu al operelor d-tale, și eu cel dântâi te stimez\n" +
                    "pentru asta, să ai o soție dulce spre a împărtăși, la căldura căminului conjugal, bucuriile senine, și, ca într-un mic\n" +
                    "paradis intim, a avea în jurul d-tale fructul amorului onest, micul îngeraș, care să te încânte cu zâmbetele și cântecele\n" +
                    "sale... Iată un sublim ideal; dar... este un dar... de la paradisul închipuit și până la infernul real, nu este, vai! decât un\n" +
                    "pas! Gândește bine în ce familie intri. Mama, o bătrână cochetă, care a ruinat doi bărbați. Zestrea promisă, o\n" +
                    "minciună sfruntată, nemaiavând decât șandramalele îngropate la Credit și pensia nenorocitului căzut victima luxului\n" +
                    "și desfrâului lor; căci mama denaturată a încurajat apucăturile fiicei — ce naște din pisică — iar aceasta mai poate în\n" +
                    "orice caz face carieră romantică, dar nu copii. Întreabă pe omul științei, un mamoș, dacă o pretinsă demoazelă de 28\n" +
                    "de ani, care până azi are la activul ei trei-patru avorturi clandestine, cunoscute de toată lumea, mai are șanse a deveni\n" +
                    "mamă. Ia bine seama! Luxul și devergondajul atâtor femei de această tristă speță au aruncat pe mulți mânuitori de\n" +
                    "bani publici, naivi ca d-ta, în brațele prăpastiei dezonoarei! Mi-am împlinit misiunea în conștiință. Rămâne ca d-ta,\n" +
                    "tânăr luminat, înainte de a face pasul fatal, să avizezi cu perspicacitate. (iscălit) Un binevoitor matur");

    void populateDb() {
        List<User> users = userIdRepository.findAll().stream().toList();
        List<Topic> topics = topicIdRepository.findAll().stream().toList();
        Random random = new Random();

        if (users.size() > 1 && topics.size() > 1) {
            String titlu = titluPostare.get(new Random().nextInt(titluPostare.size()));
            String continut = content.get(new Random().nextInt(content.size()));
                    Post post = new Post(users.get(random.nextInt(users.size())).getId(), topics.get(random.nextInt(topics.size())).getId(), titlu, continut);
            addPost(post);
            log.info("Created " + post);
        }
    }

   /* @Bean
    @Scheduled(fixedDelay = 5000L)
    void newRandomPost() {
        log.info("We have " + repository.findAll().size() + " posts");

//        if (repository.findAll().size() > 200)
//            deleteEverything();

        String titlu = titluPostare.get(new Random().nextInt(titluPostare.size()));
        String continut = "Acesta este un conținut scurt pentru postarea cu titlul \"" + titlu + "\".";
        Post post = new Post("-1", "-1", titlu, continut);
        addPost(post);
    }*/

 /*   @Bean
    @Scheduled(initialDelay = 1000L,fixedDelay = 100)
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
    }*/

    @Override
    public HashMap<String, Post> fetchAllPosts() {
        HashMap<String, Post> lista = new HashMap<>();

        List<Post> posts = repository.findAll();

        for (Post post : posts)
            lista.put(post.getId(), post);

        return lista;
    }

    @Override
    public Post fetchPostById(String id) throws PostNotFound {
        return repository.findById(id).orElseThrow(() -> new PostNotFound("Not found: " + id));
    }

    @Override
    public void addPost(Post post) {
        post.setTimestampCreated(Instant.now().getEpochSecond());
        repository.insert(post);
        sendMessage(new PostQueueMessage(QueueMessageType.CREATE, post));
        sendId(post.getId());
        // notificare care ajunge la admin-ul unui topic
        sendMessageNotification(new PostNotification(post.getCreatorId(), post.getTopicId(), QueueMessageType.CREATE, post.getId(), post.getTitle().substring(0, Math.min(post.getTitle().length(), 50))));
        if (post.getPhotos() != null) {
            for (String photo : post.getPhotos()) {
                sendMessageImage(new PhotoMessageDto("Insert",photo));
            }
        }
    }

    @Override
    public void updatePost(String id, Post post) throws PostNotFound {
        Optional<Post> postInDb = repository.findById(id);
        if(postInDb.isPresent()){
            if (postInDb.get().getPhotos() != null) {
                for (String photo : postInDb.get().getPhotos()) {
                    boolean ok = true;
                    for(String photoInFront : post.getPhotos())
                    {
                        if(Objects.equals(photoInFront, photo)){
                            ok = false;
                        }
                    }
                    if(ok) {
                        sendMessageImage(new PhotoMessageDto("Delete", photo));
                    }
                }
            }
        }
        repository.findById(id).map(elem -> {
            elem.setContent(post.getContent());
            elem.setTitle(post.getTitle());
            elem.setCreatorId(post.getCreatorId());
            elem.setTopicId(post.getTopicId());
            elem.setTimestampUpdated(Instant.now().getEpochSecond());
            repository.save(elem);
            if (post.getPhotos() != null) {
                for (String photo : post.getPhotos()) {
                    sendMessageImage(new PhotoMessageDto("Insert",photo));
                }
            }
            sendMessage(new PostQueueMessage(QueueMessageType.UPDATE, post));
            return elem;
        }).orElseThrow(() -> {
            post.setTimestampCreated(Instant.now().getEpochSecond());
            repository.insert(post);
            if (post.getPhotos() != null) {
                for (String photo : post.getPhotos()) {
                    sendMessageImage(new PhotoMessageDto("Insert",photo));
                }
            }
            sendId(post.getId());
            sendMessage(new PostQueueMessage(QueueMessageType.CREATE, post));
            return new PostNotFound("Post not found. Created one instead.");
        });
    }

    @Override
    public void deletePost(String id) throws PostNotFound {
        Optional<Post> post = repository.findById(id);
        if(post.isPresent()){
            if (post.get().getPhotos() != null) {
                for (String photo : post.get().getPhotos()) {
                    sendMessageImage(new PhotoMessageDto("Delete",photo));
                }
            }
            repository.deleteById(id);
            sendMessage(new PostQueueMessage(QueueMessageType.DELETE, post.get()));
            sendId("DEL#" + post.get().getId());
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
            // notificare care ajunge la creatorul postarii
            sendMessageNotification(new PostNotification(post.get().getCreatorId(), post.get().getTopicId(), QueueMessageType.UPVOTE, post.get().getId(), post.get().getTitle().substring(0, Math.min(post.get().getTitle().length(), 50))));
//            sendMessage(new PostQueueMessage(QueueMessageType.UPVOTE, new Post(postId, userId, "657ca7e2fb8e4725915e20c9")));
        } else
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
            sendMessageNotification(new PostNotification(post.get().getCreatorId(), post.get().getTopicId(), QueueMessageType.DOWNVOTE, post.get().getId(), post.get().getTitle().substring(0, Math.min(post.get().getTitle().length(), 50))));
        } else
            throw new PostNotFound("Post not found.");
    }
}
