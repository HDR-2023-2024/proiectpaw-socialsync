package com.socialsync.topicsmicroservice.service;

import com.google.gson.Gson;
import com.socialsync.topicsmicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.topicsmicroservice.interfaces.TopicServiceMethods;
import com.socialsync.topicsmicroservice.pojo.Topic;
import com.socialsync.topicsmicroservice.pojo.TopicQueueMessage;
import com.socialsync.topicsmicroservice.pojo.enums.QueueMessageType;
import com.socialsync.topicsmicroservice.repository.TopicRepository;
import com.socialsync.topicsmicroservice.util.exceptions.TopicNotFound;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
@EnableScheduling
public class TopicService implements TopicServiceMethods {

    private TopicRepository repository;

    private RabbitMqConnectionFactoryComponent conectionFactory;

    private AmqpTemplate amqpTemplate;

    private Gson gson;

    static List<Topic> listaTopicuri = List.of(
            new Topic("Dezbate", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam accumsan arcu quis euismod elementum. Donec laoreet dolor vel nibh sodales varius. Cras blandit fringilla euismod. Donec lobortis aliquet nisi quis ullamcorper. Vestibulum efficitur pellentesque erat eu egestas. Sed tristique libero lacinia, sollicitudin orci ut, pretium lacus. Sed dictum sem in dui ultrices, id scelerisque neque accumsan. Aliquam nec sem a erat elementum semper. Quisque massa lacus, varius ut feugiat eu, rhoncus eget ligula. Duis sollicitudin laoreet neque. Nunc faucibus vel mauris nec commodo.\n" +
                    "\n" +
                    "Aenean eu orci nulla. Praesent fermentum ante feugiat, efficitur metus id, rutrum metus. Phasellus volutpat posuere turpis, nec vulputate lectus luctus in. Donec condimentum porta metus, eget tristique augue euismod non. Vestibulum tortor dolor, interdum at lobortis sed, maximus nec elit. Aenean ac pharetra nulla. Etiam diam tortor, luctus non orci quis, iaculis vulputate dolor.\n" +
                    "\n", "", "-1"),
            new Topic("Explorare", "Sed nulla nunc, pulvinar vitae viverra et, lacinia at enim. Phasellus egestas, orci nec ultricies blandit, sapien tellus laoreet massa, non imperdiet quam arcu vel enim. Praesent ullamcorper vulputate orci eget tristique. Integer quis felis dui. Proin hendrerit mi quis semper pretium. Aenean mi orci, feugiat nec massa vel, egestas cursus lectus. Cras volutpat vitae turpis vestibulum tincidunt. Curabitur in metus vehicula, aliquet ante nec, blandit nisl. Nullam commodo dignissim maximus. Etiam congue, lectus sagittis finibus fermentum, tortor erat fringilla leo, non consectetur odio enim id mauris. Aenean vestibulum dolor at metus vulputate tempor. Sed id commodo ligula. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vivamus sit amet magna quis ante interdum tincidunt. Sed aliquet tempus sem, nec aliquet urna viverra sit amet.", "", "-1"),
            new Topic("Analiza", "Phasellus sed tellus vel magna pulvinar auctor. Nunc fermentum lobortis aliquet. Proin ligula orci, vulputate ut nibh vitae, aliquet fringilla mauris. Suspendisse potenti. Maecenas a lectus aliquet, sodales enim a, finibus ligula. Vestibulum pellentesque, nulla nec pulvinar varius, justo nibh ultricies nisl, sit amet bibendum tortor est et eros. Duis id bibendum mi. Maecenas id tellus maximus, bibendum risus hendrerit, posuere nunc. In sagittis ante dui, bibendum tincidunt lectus blandit ut. Suspendisse accumsan quis erat vitae tempor. Aenean ut cursus ex. Curabitur at tortor in sapien volutpat sollicitudin. Sed quis lorem id lacus lobortis ullamcorper.\n" +
                    "\n" +
                    "Sed lectus nisi, sollicitudin hendrerit porta id, ultrices et elit. Integer volutpat convallis elementum. Nulla facilisi. Morbi ut lorem lectus. Maecenas congue magna a eleifend porta. Duis quis nulla eu sapien ultricies tempus a quis nibh. Aenean vel ullamcorper felis. Vestibulum hendrerit tincidunt libero, ornare accumsan dolor euismod semper.", "", "-1"),
            new Topic("Discuție", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque condimentum urna tempor turpis dapibus dictum. Nullam ac lectus et orci bibendum elementum ut eu odio. Donec ac sollicitudin metus. Nulla eros nunc, eleifend vel erat sit amet, efficitur sagittis enim. Sed nec mattis urna, in semper nisl. Aliquam mollis leo eget felis suscipit sodales. Donec gravida facilisis neque in vehicula. Aliquam mollis justo imperdiet, rutrum quam sit amet, egestas velit. Integer finibus at mauris non interdum. Vivamus ornare, orci luctus dignissim pulvinar, tortor orci mollis massa, eget lacinia nibh erat nec purus. Phasellus posuere elit sit amet molestie mollis.", "", "-1"),
            new Topic("Cercetare", "Phasellus id dolor sagittis, facilisis metus quis, tincidunt ante. Mauris ut tellus dapibus, facilisis dolor non, porttitor purus. Morbi sed ante tincidunt, condimentum neque sit amet, semper purus. Sed condimentum lorem nec finibus congue. Nunc placerat malesuada eros, vitae scelerisque dui. Morbi sed purus nibh. Sed at nibh et velit vulputate fermentum ut at augue. Nam elementum, nisi quis tristique rhoncus, erat augue tempus ante, nec sodales diam nunc sit amet arcu. Proin ultricies, purus eget imperdiet molestie, urna metus tempor est, non mollis lectus nulla in turpis. Nulla id nisi vel tortor rutrum venenatis. Integer a risus sit amet eros eleifend dictum sed lobortis tortor. Aliquam maximus sit amet risus ut tempus. Suspendisse non nibh non lectus facilisis consequat. In hac habitasse platea dictumst. Nullam facilisis metus sed justo imperdiet faucibus. Proin dictum eros leo, sit amet luctus eros placerat id.\n" +
                    "\n","", "-1"),
            new Topic("Opinie", "Etiam vitae fringilla quam. Proin accumsan consectetur mauris, condimentum scelerisque libero ultricies nec. Fusce semper, libero in scelerisque tristique, sem libero semper magna, venenatis tristique mauris dui at odio. Quisque in leo a justo pulvinar bibendum. Nullam dignissim nulla et lectus ornare auctor. Nulla facilisi. Suspendisse feugiat vitae lacus et rutrum. Fusce vestibulum sapien rhoncus, cursus erat in, laoreet eros. Donec nec felis nec purus molestie sollicitudin. Curabitur et tempus augue. Aenean id est sed tellus aliquam varius.\n" +
                    "\n" +
                    "Donec aliquam dui at metus viverra, a fermentum dolor commodo. Quisque a diam malesuada, cursus odio et, scelerisque velit. Ut nec massa mattis, sodales urna eu, ornare arcu. Duis tincidunt tristique sapien in dictum. Sed eleifend id mauris sed egestas. Aenean ac ex non ante dignissim interdum ut ut felis. Cras suscipit molestie purus, id ornare nibh ornare eu. Mauris sagittis a nibh at efficitur.", "", "-1"),
            new Topic("Sondaj", "Donec tempor gravida nunc. Nam vel venenatis massa. In hac habitasse platea dictumst. Morbi pretium nulla non mollis pharetra. Quisque gravida quam leo, et feugiat nisi convallis vitae. Integer at elit a massa lobortis laoreet quis nec leo. Maecenas et turpis at lectus ornare imperdiet. Suspendisse et urna id nisl finibus consectetur. Nam quis orci vitae libero vulputate dictum sed et lacus. Proin non enim elementum, pharetra risus sit amet, faucibus libero. Integer eros neque, cursus sed gravida in, convallis at turpis. Etiam a enim varius, sollicitudin ipsum id, pulvinar tellus. Vivamus sit amet lorem tortor.\n" +
                    "\n" +
                    "Praesent in gravida dolor. Proin vel metus sapien. Suspendisse luctus eget elit a suscipit. Pellentesque orci enim, accumsan vel nunc ut, porta dictum ex. Praesent semper eleifend ipsum vel dictum. Etiam et commodo orci, non rutrum nibh. Proin ultricies condimentum pretium. Donec et consequat lorem. Aliquam convallis scelerisque arcu, vitae interdum ligula molestie ut.", "", "-1"),
            new Topic("Dezbatere", "Interdum et malesuada fames ac ante ipsum primis in faucibus. Duis ullamcorper egestas felis a tincidunt. Nullam ac neque laoreet nunc molestie maximus. Pellentesque odio massa, aliquam ornare nibh in, varius pretium erat. Cras commodo imperdiet cursus. Praesent vehicula quis risus et pharetra. Nullam ipsum ipsum, maximus id tellus ut, pharetra tempor justo. Duis rhoncus, elit pellentesque tempus commodo, quam est interdum ipsum, quis fermentum turpis diam sed felis. Vestibulum id mi id nisl egestas sagittis. Sed sit amet tincidunt justo, ac gravida nunc. Donec lacinia sapien dolor, a sagittis nulla sollicitudin et. Aenean eu nisl laoreet, elementum tortor sit amet, auctor tortor. Nullam feugiat libero ut libero semper, eget cursus nisi tempus.","", "-1"),
            new Topic("Analiză", "Donec ac nulla dapibus, congue libero at, imperdiet lacus. Sed vel metus pretium, dictum sapien et, aliquam lacus. Pellentesque semper purus in velit laoreet rhoncus. In gravida, felis id tempor vehicula, orci lectus sollicitudin diam, non tempor elit diam a odio. Ut interdum sem lorem, ac pharetra arcu ultrices sed. Proin et mi sit amet enim feugiat lacinia. Donec varius at diam quis iaculis. Fusce ac pellentesque dui. Nulla quis porta magna, in rutrum diam. Mauris magna dui, viverra non elit ac, laoreet viverra risus. Donec vitae volutpat sem. Maecenas libero magna, fringilla nec mauris a, ornare laoreet neque. Integer non diam elit. Quisque tempor, quam eu cursus mollis, lectus erat cursus augue, in tempor nisl lectus quis mi. Donec id nisl nunc. Duis ac arcu ac nibh tincidunt sollicitudin rhoncus quis lectus.\n" +
                    "\n" +
                    "Vivamus imperdiet dolor erat, nec gravida orci consequat a. Ut eu venenatis purus. In in rhoncus nibh. Maecenas ultrices congue commodo. Morbi a eros tincidunt, cursus ipsum id, facilisis neque. Mauris sed gravida dolor. Mauris efficitur, tellus eget posuere porttitor, magna massa posuere ipsum, sed accumsan odio sapien eget ex. In non eleifend dui. Proin eget tincidunt nibh, imperdiet finibus risus. Nulla cursus dui libero, id ultrices ipsum vehicula vel.", "", "-1"),
            new Topic("Cercetare", "Vivamus imperdiet dolor erat, nec gravida orci consequat a. Ut eu venenatis purus. In in rhoncus nibh. Maecenas ultrices congue commodo. Morbi a eros tincidunt, cursus ipsum id, facilisis neque. Mauris sed gravida dolor. Mauris efficitur, tellus eget posuere porttitor, magna massa posuere ipsum, sed accumsan odio sapien eget ex. In non eleifend dui. Proin eget tincidunt nibh, imperdiet finibus risus. Nulla cursus dui libero, id ultrices ipsum vehicula vel.\n" +
                    "\n" +
                    "Sed eget lorem quis neque rutrum pretium. Nunc volutpat molestie lacus, id congue sapien mollis ut. Pellentesque at ex quis ante lobortis dapibus. Maecenas sodales rutrum augue sit amet lacinia. Duis tortor odio, volutpat et est sit amet, ultrices tincidunt elit. Maecenas eget orci quis purus euismod placerat non in nisl. Integer ac elit tempus ex faucibus lacinia.", "", "-1")
    );

    @Bean
    void initTemplate() {
        this.amqpTemplate = conectionFactory.rabbitTemplate();
    }

    private void sendMessage(TopicQueueMessage topic) {
        String json = gson.toJson(topic);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKey(), json);
    }

    void deleteEverything() {
        repository.findAll().forEach(x -> {
            try {
                deleteTopic(x.getId());
            } catch (TopicNotFound e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Bean
    void populateDb() {
        deleteEverything();
        listaTopicuri.forEach(this::addTopic);
    }

  /*  @Bean
    @Scheduled(fixedDelay = 30000L)
    void newRandomTopic() {
        log.info("We have " + repository.findAll().size() + " topic");

//        if (repository.findAll().size() > 50)
//            deleteEverything();

        Topic topic = listaTopicuri.get(new Random().nextInt(listaTopicuri.size()));
        Topic newTopic = new Topic();
        newTopic.setName(topic.getName() + new Random().nextInt(10000));
        newTopic.setDescription(topic.getDescription() + new Random().nextInt(10000));
        newTopic.setCreatorId("-1");
        addTopic(newTopic);
    }
*/
    @Override
    public HashMap<String, Topic> fetchAllTopics() {
        HashMap<String, Topic> all_topics = new HashMap<>();

        List<Topic> topics = repository.findAll();

        for (Topic topic : topics)
            all_topics.put(topic.getId(), topic);
        return all_topics;
    }

    @Override
    public Topic fetchTopicById(String id) throws TopicNotFound {
        return repository.findById(id).orElseThrow(() -> new TopicNotFound("Not fount " + id));
    }

    @Override
    public void addTopic(Topic topic) {
        topic.setTimestampCreated(Instant.now().getEpochSecond());
        repository.save(topic);
        sendMessage(new TopicQueueMessage(QueueMessageType.CREATE, topic));
    }

    @Override
    public void updateTopic(String id, Topic topic) throws TopicNotFound {
        repository.findById(id).map(elem -> {
            elem.setName(topic.getName());
            elem.setDescription(topic.getDescription());
            elem.setPhotoId(topic.getPhotoId());
            elem.setCreatorId(topic.getCreatorId());
            elem.setTimestampUpdated(Instant.now().getEpochSecond());
            repository.save(elem);
            sendMessage(new TopicQueueMessage(QueueMessageType.UPDATE, topic));
            return elem;
        }).orElseThrow(() -> {
            topic.setTimestampCreated(Instant.now().getEpochSecond());
            topic.setId(null);
            repository.insert(topic);
            sendMessage(new TopicQueueMessage(QueueMessageType.CREATE, topic));
            return new TopicNotFound("Topic not found. Created one instead.");
        });
    }

    @Override
    public void deleteTopic(String id) throws TopicNotFound {
        Optional<Topic> topic = repository.findById(id);

        if (topic.isPresent()) {
            repository.deleteById(id);
            sendMessage(new TopicQueueMessage(QueueMessageType.DELETE, topic.get()));
        } else
            throw new TopicNotFound("Topic not found.");
    }
}
