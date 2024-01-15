package com.socialsync.usersmicroservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.socialsync.usersmicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.usersmicroservice.exceptions.NotAcceptableException;
import com.socialsync.usersmicroservice.exceptions.NotFoundException;
import com.socialsync.usersmicroservice.exceptions.UnauthorizedException;
import com.socialsync.usersmicroservice.interfaces.UsersServiceMethods;
import com.socialsync.usersmicroservice.pojo.*;
import com.socialsync.usersmicroservice.pojo.enums.GenderType;
import com.socialsync.usersmicroservice.pojo.enums.QueueMessageType;
import com.socialsync.usersmicroservice.pojo.enums.RoleType;
import com.socialsync.usersmicroservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import io.jsonwebtoken.Claims;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
@EnableScheduling
@Slf4j
public class UsersService implements UsersServiceMethods {
    private UserRepository repository;
    private AuthorizationService authorizationService;

    private RabbitMqConnectionFactoryComponent conectionFactory;
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);
    private AmqpTemplate amqpTemplate;
    private static HashMap<String, String> hashMap;
    private Gson gson;

    static List<User> users = List.of(
            new User("madab", "madab.123", "boacamadalinaelena@gmail.com", GenderType.woman, null, "Morbi ultrices, justo id tempus tristique, massa ante tempus diam, at fringilla dolor lacus at metus. Donec sed ipsum vitae velit convallis imperdiet. In lectus massa, aliquet at massa convallis, viverra suscipit lectus. Nunc vel ligula erat. Sed consequat, leo eget pretium faucibus, felis ipsum maximus orci, consequat faucibus leo felis ut augue. Praesent semper eros a nunc ullamcorper, nec viverra lectus semper. Praesent mattis vitae neque eu luctus.", RoleType.user),
            new User("timofteIorgu", "myPassword", "timofteIorgu@gmail.com", GenderType.man, null, "Morbi ultrices, justo id tempus tristique, massa ante tempus diam, at fringilla dolor lacus at metus. Donec sed ipsum vitae velit convallis imperdiet. In lectus massa, aliquet at massa convallis, viverra suscipit lectus. Nunc vel ligula erat. Sed consequat, leo eget pretium faucibus, felis ipsum maximus orci, consequat faucibus leo felis ut augue. Praesent semper eros a nunc ullamcorper, nec viverra lectus semper. Praesent mattis vitae neque eu luctus.", RoleType.admin),
            new User("ciocoiuMatei", "imiPlacParoleleComplicate", "ciocoiuMatei@gmail.com", GenderType.man, null, "Morbi ultrices, justo id tempus tristique, massa ante tempus diam, at fringilla dolor lacus at metus. Donec sed ipsum vitae velit convallis imperdiet. In lectus massa, aliquet at massa convallis, viverra suscipit lectus. Nunc vel ligula erat. Sed consequat, leo eget pretium faucibus, felis ipsum maximus orci, consequat faucibus leo felis ut augue. Praesent semper eros a nunc ullamcorper, nec viverra lectus semper. Praesent mattis vitae neque eu luctus.", RoleType.admin),
            new User("tomaIon", "tomaIonPassword1234", "tomaIon@gmail.com", GenderType.man, null, "Donec euismod aliquam neque eget rhoncus. Ut convallis ac ante a pulvinar. Quisque et aliquam nisi. Donec vehicula sed dolor nec dapibus. Morbi vestibulum lacus quam, eu interdum massa luctus eget. Nullam eu nibh at mauris scelerisque rutrum et quis ex. Nunc sollicitudin, nibh a molestie malesuada, neque lectus pulvinar libero, id luctus mi sem sed lacus. Nullam imperdiet, ipsum sagittis tempor tincidunt, tortor est finibus purus, ut interdum dui metus et augue. Aenean tincidunt risus urna, et fermentum odio vestibulum quis. Sed ornare nulla in metus malesuada dictum. Morbi ac nisi sit amet massa finibus fringilla. Phasellus porta odio eu erat porta, et euismod turpis ullamcorper. Integer at risus lacinia, aliquet velit a, aliquam lorem. Nulla eget dignissim quam. Maecenas tincidunt, magna id fringilla accumsan, magna nunc aliquet lectus, a maximus metus est sollicitudin libero.", RoleType.user),
            new User("tomaMaria", "tomaMaria.12CatelusCuParulCret", "tomaMaria@gmail.com", GenderType.woman, "Morbi ultrices, justo id tempus tristique, massa ante tempus diam, at fringilla dolor lacus at metus. Donec sed ipsum vitae velit convallis imperdiet. In lectus massa, aliquet at massa convallis, viverra suscipit lectus. Nunc vel ligula erat. Sed consequat, leo eget pretium faucibus, felis ipsum maximus orci, consequat faucibus leo felis ut augue. Praesent semper eros a nunc ullamcorper, nec viverra lectus semper. Praesent mattis vitae neque eu luctus.", null, RoleType.user),
            new User("johnDoe", "johnDoe123!Pass", "johnDoe@example.com", GenderType.man, null, "Donec euismod aliquam neque eget rhoncus. Ut convallis ac ante a pulvinar. Quisque et aliquam nisi. Donec vehicula sed dolor nec dapibus. Morbi vestibulum lacus quam, eu interdum massa luctus eget. Nullam eu nibh at mauris scelerisque rutrum et quis ex. Nunc sollicitudin, nibh a molestie malesuada, neque lectus pulvinar libero, id luctus mi sem sed lacus. Nullam imperdiet, ipsum sagittis tempor tincidunt, tortor est finibus purus, ut interdum dui metus et augue. Aenean tincidunt risus urna, et fermentum odio vestibulum quis. Sed ornare nulla in metus malesuada dictum. Morbi ac nisi sit amet massa finibus fringilla. Phasellus porta odio eu erat porta, et euismod turpis ullamcorper. Integer at risus lacinia, aliquet velit a, aliquam lorem. Nulla eget dignissim quam. Maecenas tincidunt, magna id fringilla accumsan, magna nunc aliquet lectus, a maximus metus est sollicitudin libero.", RoleType.user),
            new User("janeSmith", "janeSmith@456", "janeSmith@gmail.com", GenderType.woman, null, "Suspendisse risus nunc, ultricies cursus dui quis, malesuada facilisis ex. Integer imperdiet purus vel ligula sagittis commodo. Phasellus tristique nunc libero, fringilla pretium dui euismod quis. Duis vulputate lorem et nunc ultricies, sit amet rhoncus metus sagittis. Aliquam lacinia ultrices ultrices. Aenean dictum consequat arcu. Fusce pulvinar diam non dolor fermentum, a rutrum nibh consectetur. Duis imperdiet ac leo eu lacinia. Proin congue egestas ipsum, eu posuere est.", RoleType.user),
            new User("alexJohnson", "alexJohnsonPass", "alexJohnson@yahoo.com", GenderType.woman, null, "Sed consequat velit id eros sodales ultricies. Duis nec lacinia turpis, vel facilisis diam. Pellentesque facilisis arcu sit amet lectus vehicula laoreet. Etiam ornare accumsan vestibulum. Ut finibus ornare orci sed facilisis. In eget ex metus. Nullam eu diam in mauris accumsan vehicula a at neque. Suspendisse fringilla erat nec euismod sodales. Nam molestie diam nec lacus porttitor volutpat. Quisque lobortis scelerisque velit nec euismod. Etiam elit lacus, vulputate in mattis ac, ultrices at nibh.\n" +
                    "\n", RoleType.user),
            new User("emilyBrown", "emilyBrownPwd", "emilyBrown@hotmail.com", GenderType.woman, null, "Donec dapibus velit massa, facilisis euismod nisi consectetur nec. Donec a placerat diam. Aenean porttitor lacus eget ante blandit, sed accumsan ex vestibulum. Phasellus elit sapien, maximus vel turpis vel, condimentum facilisis quam. Suspendisse potenti. Integer pharetra feugiat libero, sit amet venenatis libero eleifend at. Pellentesque posuere fermentum ex nec interdum. Nam ex ligula, laoreet eu tincidunt eu, semper at nisi. Sed a turpis sed turpis convallis vehicula. Nulla eu lacus id sapien tempus egestas a et ipsum. Nam tincidunt neque quis gravida tincidunt. Integer blandit lectus massa, ac tincidunt nisi auctor id. Nulla ligula ex, dapibus id turpis at, convallis congue ante. Cras sed sem fringilla, elementum metus in, accumsan nulla. Nam imperdiet ullamcorper est, at faucibus elit eleifend a. Nulla dui elit, malesuada et vehicula nec, bibendum at mauris.\n" +
                    "\n", RoleType.user),
            new User("michaelClark", "michaelClarkPass", "michaelClark@gmail.com", GenderType.man, null, "Mauris sagittis ut quam quis placerat. Phasellus placerat tellus sed posuere finibus. Sed vestibulum elit in ex molestie cursus. Praesent sagittis neque lacinia semper vehicula. Fusce commodo ligula magna, id viverra mauris fringilla non. Duis vestibulum diam justo, sit amet aliquam erat pharetra a. Interdum et malesuada fames ac ante ipsum primis in faucibus. Curabitur a lacus ligula. Ut ac nisi tempus, porttitor sem elementum, condimentum leo. Suspendisse non sem imperdiet dolor maximus porttitor. Integer placerat dolor libero, ac iaculis enim volutpat vel. Donec non diam at orci bibendum placerat. Proin sodales varius quam, sit amet mollis metus pellentesque id. Integer commodo massa nec mi ultricies interdum.", RoleType.user),
            new User("sarahWhite", "sarahWhitePwd", "sarahWhite@yahoo.com", GenderType.woman, null, "Aliquam egestas ante ut nisi faucibus eleifend. Integer mollis risus non neque efficitur, at molestie orci aliquam. Quisque lacus mi, ultricies at magna id, malesuada imperdiet nisi. Duis ornare rhoncus dui nec facilisis. Phasellus viverra urna eu risus bibendum ultricies. Sed elit diam, ornare eleifend mollis at, pulvinar sed risus. Mauris sem erat, venenatis quis semper sed, porta vel mi. Fusce semper turpis vitae lectus pulvinar, in tempus nisi pharetra. Donec vel suscipit diam, et viverra leo. In non tempus eros. Suspendisse sit amet lorem sit amet enim egestas viverra et ac libero. Fusce tincidunt libero orci, malesuada posuere est sodales non. Duis imperdiet, magna nec mollis aliquam, ante neque mattis dui, nec feugiat quam neque sit amet justo. Donec bibendum ipsum enim, vel lacinia enim dignissim et.", RoleType.user),
            new User("davidTaylor", "davidTaylor123", "davidTaylor@hotmail.com", GenderType.man, null, "Curabitur vel dictum sapien, et congue dui. Curabitur gravida velit id purus porttitor, vel faucibus est elementum. Quisque dapibus sapien et bibendum lacinia. In hac habitasse platea dictumst. Curabitur vel velit malesuada, fermentum nulla ut, egestas diam. Integer placerat libero porttitor, posuere metus et, ultricies mauris. Nulla ac lacinia ipsum. Nulla vehicula est non enim elementum, ac sagittis sapien commodo. Integer mollis at arcu vitae dictum.", RoleType.user),
            new User("oliviaMartin", "oliviaMartinPwd", "oliviaMartin@gmail.com", GenderType.woman, null, "Proin eleifend porta tellus luctus viverra. Suspendisse porttitor turpis a enim malesuada, id pretium est tincidunt. Phasellus rutrum euismod dolor, mollis maximus metus commodo consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin luctus nisi lorem, a rutrum nunc rutrum eget. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam ac arcu rutrum, convallis sem non, luctus massa. Sed sed nunc ex. Quisque at mauris condimentum, venenatis velit ac, sagittis eros. Nullam sapien metus, sollicitudin sed pellentesque non, cursus in lectus. Fusce cursus felis et dolor convallis, sed dictum sem posuere. Nullam nec lobortis ipsum. Cras sit amet finibus turpis, id hendrerit mi. Maecenas aliquet neque ac purus ullamcorper, at facilisis ipsum faucibus. Vivamus facilisis sodales mauris, eget varius ipsum pretium id.", RoleType.user),
            new User("chrisMiller", "chrisMillerPwd", "chrisMiller@yahoo.com", GenderType.man, null, "Nullam maximus ultricies nunc ut tincidunt. Ut sit amet rutrum turpis. Donec vel ligula diam. Cras at tortor eu lorem feugiat laoreet sed sed erat. Phasellus rhoncus velit sodales est feugiat pretium. In est leo, euismod egestas nulla eu, elementum varius nibh. Praesent pulvinar vitae est eu cursus. Vivamus metus nisl, lobortis vel libero in, malesuada suscipit orci. Sed odio sem, rhoncus non sem vitae, vehicula volutpat nibh. Donec molestie interdum urna, eget aliquam mi venenatis vitae. Pellentesque id auctor orci, nec pharetra libero.", RoleType.user),
            new User("amandaYoung", "amandaYoungPass", "amandaYoung@hotmail.com", GenderType.woman, null, "Curabitur vel dictum sapien, et congue dui. Curabitur gravida velit id purus porttitor, vel faucibus est elementum. Quisque dapibus sapien et bibendum lacinia. In hac habitasse platea dictumst. Curabitur vel velit malesuada, fermentum nulla ut, egestas diam. Integer placerat libero porttitor, posuere metus et, ultricies mauris. Nulla ac lacinia ipsum. Nulla vehicula est non enim elementum, ac sagittis sapien commodo. Integer mollis at arcu vitae dictum.", RoleType.user),
            new User("AnaPopescu", "ParolaAna123!", "ana.popescu@example.com", GenderType.woman, null, "Mauris sagittis ut quam quis placerat. Phasellus placerat tellus sed posuere finibus. Sed vestibulum elit in ex molestie cursus. Praesent sagittis neque lacinia semper vehicula. Fusce commodo ligula magna, id viverra mauris fringilla non. Duis vestibulum diam justo, sit amet aliquam erat pharetra a. Interdum et malesuada fames ac ante ipsum primis in faucibus. Curabitur a lacus ligula. Ut ac nisi tempus, porttitor sem elementum, condimentum leo. Suspendisse non sem imperdiet dolor maximus porttitor. Integer placerat dolor libero, ac iaculis enim volutpat vel. Donec non diam at orci bibendum placerat. Proin sodales varius quam, sit amet mollis metus pellentesque id. Integer commodo massa nec mi ultricies interdum.", RoleType.user),
            new User("AndreiIonescu", "IonescuPass456", "andrei.ionescu@example.com", GenderType.man, null, "Sed consequat velit id eros sodales ultricies. Duis nec lacinia turpis, vel facilisis diam. Pellentesque facilisis arcu sit amet lectus vehicula laoreet. Etiam ornare accumsan vestibulum. Ut finibus ornare orci sed facilisis. In eget ex metus. Nullam eu diam in mauris accumsan vehicula a at neque. Suspendisse fringilla erat nec euismod sodales. Nam molestie diam nec lacus porttitor volutpat. Quisque lobortis scelerisque velit nec euismod. Etiam elit lacus, vulputate in mattis ac, ultrices at nibh.\n" +
                    "\n", RoleType.user),
            new User("ElenaVasilescu", "ElenaPass789", "elena.vasilescu@example.com", GenderType.woman, null, "Suspendisse risus nunc, ultricies cursus dui quis, malesuada facilisis ex. Integer imperdiet purus vel ligula sagittis commodo. Phasellus tristique nunc libero, fringilla pretium dui euismod quis. Duis vulputate lorem et nunc ultricies, sit amet rhoncus metus sagittis. Aliquam lacinia ultrices ultrices. Aenean dictum consequat arcu. Fusce pulvinar diam non dolor fermentum, a rutrum nibh consectetur. Duis imperdiet ac leo eu lacinia. Proin congue egestas ipsum, eu posuere est.", RoleType.user),
            new User("MariusDumitrescu", "MariusPass123", "marius.dumitrescu@example.com", GenderType.man, null, "Donec euismod aliquam neque eget rhoncus. Ut convallis ac ante a pulvinar. Quisque et aliquam nisi. Donec vehicula sed dolor nec dapibus. Morbi vestibulum lacus quam, eu interdum massa luctus eget. Nullam eu nibh at mauris scelerisque rutrum et quis ex. Nunc sollicitudin, nibh a molestie malesuada, neque lectus pulvinar libero, id luctus mi sem sed lacus. Nullam imperdiet, ipsum sagittis tempor tincidunt, tortor est finibus purus, ut interdum dui metus et augue. Aenean tincidunt risus urna, et fermentum odio vestibulum quis. Sed ornare nulla in metus malesuada dictum. Morbi ac nisi sit amet massa finibus fringilla. Phasellus porta odio eu erat porta, et euismod turpis ullamcorper. Integer at risus lacinia, aliquet velit a, aliquam lorem. Nulla eget dignissim quam. Maecenas tincidunt, magna id fringilla accumsan, magna nunc aliquet lectus, a maximus metus est sollicitudin libero.", RoleType.user),
            new User("SimonaGheorghiu", "SimonaPwd456", "simona.gheorghiu@example.com", GenderType.woman, null, "Morbi ultrices, justo id tempus tristique, massa ante tempus diam, at fringilla dolor lacus at metus. Donec sed ipsum vitae velit convallis imperdiet. In lectus massa, aliquet at massa convallis, viverra suscipit lectus. Nunc vel ligula erat. Sed consequat, leo eget pretium faucibus, felis ipsum maximus orci, consequat faucibus leo felis ut augue. Praesent semper eros a nunc ullamcorper, nec viverra lectus semper. Praesent mattis vitae neque eu luctus.", RoleType.user),
            new User("CristianRadu", "Cristian123!", "cristian.radu@example.com", GenderType.man, null, "Donec dapibus velit massa, facilisis euismod nisi consectetur nec. Donec a placerat diam. Aenean porttitor lacus eget ante blandit, sed accumsan ex vestibulum. Phasellus elit sapien, maximus vel turpis vel, condimentum facilisis quam. Suspendisse potenti. Integer pharetra feugiat libero, sit amet venenatis libero eleifend at. Pellentesque posuere fermentum ex nec interdum. Nam ex ligula, laoreet eu tincidunt eu, semper at nisi. Sed a turpis sed turpis convallis vehicula. Nulla eu lacus id sapien tempus egestas a et ipsum. Nam tincidunt neque quis gravida tincidunt. Integer blandit lectus massa, ac tincidunt nisi auctor id. Nulla ligula ex, dapibus id turpis at, convallis congue ante. Cras sed sem fringilla, elementum metus in, accumsan nulla. Nam imperdiet ullamcorper est, at faucibus elit eleifend a. Nulla dui elit, malesuada et vehicula nec, bibendum at mauris.\n" +
                    "\n", RoleType.user),
            new User("AlexandraStanescu", "AlexandraPwd789", "alexandra.stanescu@example.com", GenderType.woman, null, "Mauris sagittis ut quam quis placerat. Phasellus placerat tellus sed posuere finibus. Sed vestibulum elit in ex molestie cursus. Praesent sagittis neque lacinia semper vehicula. Fusce commodo ligula magna, id viverra mauris fringilla non. Duis vestibulum diam justo, sit amet aliquam erat pharetra a. Interdum et malesuada fames ac ante ipsum primis in faucibus. Curabitur a lacus ligula. Ut ac nisi tempus, porttitor sem elementum, condimentum leo. Suspendisse non sem imperdiet dolor maximus porttitor. Integer placerat dolor libero, ac iaculis enim volutpat vel. Donec non diam at orci bibendum placerat. Proin sodales varius quam, sit amet mollis metus pellentesque id. Integer commodo massa nec mi ultricies interdum.", RoleType.user),
            new User("AdrianMoldovan", "Adrian456!", "adrian.moldovan@example.com", GenderType.man, null, "Curabitur vel dictum sapien, et congue dui. Curabitur gravida velit id purus porttitor, vel faucibus est elementum. Quisque dapibus sapien et bibendum lacinia. In hac habitasse platea dictumst. Curabitur vel velit malesuada, fermentum nulla ut, egestas diam. Integer placerat libero porttitor, posuere metus et, ultricies mauris. Nulla ac lacinia ipsum. Nulla vehicula est non enim elementum, ac sagittis sapien commodo. Integer mollis at arcu vitae dictum.\n" +
                    "\n", RoleType.user)
    );

    public void deleteData() {
        repository.findAll().forEach(x -> deleteUser(x.getId()));
    }

    @Bean
    public void populateDb() throws NotAcceptableException {
        deleteData();
        for (User u : users) {
            addUser(u);
            log.info("Added " + u);
        }
    }

    static {
        hashMap = new HashMap<>();
    }

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /* @Scheduled(fixedDelay = 30000L)
     public void newRandomUser() throws NotAcceptableException {
         log.info("We have " + repository.findAll().size() + " users!");
     //    if (repository.findAll().size() > 50)
      //       deleteData();

         User randomUser = users.get(new Random().nextInt(users.size()));
         User newUser = new User();
         newUser.setUsername(randomUser.getUsername() + new Random().nextInt(10000));
         newUser.setPassword(randomUser.getPassword());
         newUser.setEmail(new Random().nextInt(10000) + randomUser.getEmail());
         newUser.setRole(randomUser.getRole());
         newUser.setGender(randomUser.getGender());
         addUser(newUser);
     }
 */
    @Bean
    void initTemplate() {
        this.amqpTemplate = conectionFactory.rabbitTemplate();
    }

    private void sendMessage(UserQueueMessage user) {
        String json = gson.toJson(user);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKeyTopics(), json);
    }

    private void sendId(String id) {
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKeyIdsForComments(), id);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKeyIdsForPosts(), id);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKeyIdsForTopics(), id);
    }

    @Override
    public HashMap<String, UserSelect> fetchAllUsers() {
        HashMap<String, UserSelect> list = new HashMap<>();
        List<User> users = repository.findAll();
        for (User user : users)
            list.put(user.getId(), new UserSelect(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getPhotoId(), user.getDescription(), user.getGender()));
        return list;
    }

    @Override
    public UserSelect fetchUserById(String id) throws RuntimeException {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found: " + id));
        return new UserSelect(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getPhotoId(), user.getDescription(), user.getGender());
    }

    @Override
    public void addUser(User user) throws NotAcceptableException {
        user.setPassword(this.passwordEncoder().encode(user.getPassword()));
        try {
            if (user.getPhotoId() == null) {
                Random rand = new Random();
                user.setPhotoId(String.valueOf(rand.nextInt(10)));
            }
            repository.save(user);
            sendId(user.getId());
            sendMessage(new UserQueueMessage(QueueMessageType.CREATE, user));
        } catch (Exception ex) {
            if (ex.getMessage().contains("UK_ob8kqyqqgmefl0aco34akdtpe")) {
                throw new NotAcceptableException("There is already a user with this email.");
            } else if (ex.getMessage().contains("UK_sb8bbouer5wak8vyiiy4pf2bx")) {
                throw new NotAcceptableException("There is already a user with this username.");
            }
        }
    }

    @Override
    public void updateUser(String id, UserSelect user) throws RuntimeException, NotFoundException, NotAcceptableException {
        Optional<User> userInDb = repository.findById(id);
        if (userInDb.isPresent()) {
            try {
                if (user.getPhotoId() == null) {
                    Random rand = new Random();
                    user.setPhotoId(String.valueOf(rand.nextInt(10)));
                }
                repository.updateUser(id, user.getUsername(), user.getEmail(), user.getRole(), user.getGender(), user.getDescription(), user.getPhotoId());
                sendMessage(new UserQueueMessage(QueueMessageType.UPDATE, new User(id, user.getUsername(), null, user.getEmail(), user.getGender(), user.getPhotoId(), user.getDescription(), user.getRole())));
            } catch (Exception ex) {
                if (ex.getMessage().contains("uk_email")) {
                    throw new NotAcceptableException("There is already a user with this email.");
                } else if (ex.getMessage().contains("uk_username")) {
                    throw new NotAcceptableException("There is already a user with this username.");
                }
            }
        } else {
            throw new NotFoundException("User not found.");
        }
    }

    @Override
    public void updatePassword(String id, String password) throws RuntimeException {
        try {
            this.repository.updatePassword(id, passwordEncoder().encode(password));
        } catch (Exception ex) {
            throw new RuntimeException("User not found.");
        }
    }

    @Override
    public void deleteUser(String id) throws RuntimeException {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            repository.deleteById(id);
            sendId("DEL#"+user.get().getId());
            sendMessage(new UserQueueMessage(QueueMessageType.DELETE, user.get()));
        } else
            throw new RuntimeException("Created one instead.");
    }

    @Override
    public AuthorizedInfo login(Credentials credentials) throws RuntimeException {
        Optional<User> user = repository.findByUsername(credentials.getUsername());
        if (user.isPresent()) {
            if (this.passwordEncoder().matches(credentials.getPassword(), user.get().getPassword())) {
                return new AuthorizedInfo(user.get().getId(), user.get().getRole().name());
            }
        }
        throw new RuntimeException("Unauthorized");
    }

    public boolean sendCodeResetPassword(String email) throws JsonProcessingException {
        Random random = new Random();
        // cod de 6 cifre
        int code = random.nextInt(900000) + 100000;

        String url = "http://notify-microservice:8090/notification/send-reset-password";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String message = "Salutare!\n\nAți solicitat resetarea parolei pentru site-ul SocialSync.\n\nIntroduce-ți cod-ul: \"" + +code + "\" pe site pentru a începe resetarea parolei.\n\nCu drag, echipa SocialSync.";
        String subject = "Resetare parolă";
        NotificationRequest notificationRequest = new NotificationRequest(email,message,subject);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(notificationRequest);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        if (statusCode == HttpStatus.OK) {
            hashMap.put(email, code + "");
            return true;
        } else {
            return false;
        }
    }

    public AuthorizedResponseDto validateCode(ValidateCode validateCode) throws UnauthorizedException {
        if (Objects.equals(hashMap.get(validateCode.getEmail()), validateCode.getCode())) {
            Optional<User> user = this.repository.findByEmail(validateCode.getEmail());
            try {
                if (user.isPresent()) {
                   return  new AuthorizedResponseDto("Bearer " + authorizationService.getJwt(new AuthorizedInfo(user.get().getId(), user.get().getRole().name())),user.get().getUsername(),user.get().getPhotoId(),user.get().getId(),user.get().getRole().toString());
                } else {
                    throw new UnauthorizedException();
                }
            } catch (Exception ex) {
                throw new UnauthorizedException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }
}
