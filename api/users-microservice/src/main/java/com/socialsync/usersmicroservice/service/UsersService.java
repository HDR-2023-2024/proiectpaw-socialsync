package com.socialsync.usersmicroservice.service;

import com.google.gson.Gson;
import com.socialsync.usersmicroservice.components.RabbitMqConnectionFactoryComponent;
import com.socialsync.usersmicroservice.interfaces.UsersServiceMethods;
import com.socialsync.usersmicroservice.pojo.User;
import com.socialsync.usersmicroservice.pojo.UserQueueMessage;
import com.socialsync.usersmicroservice.pojo.UserSelect;
import com.socialsync.usersmicroservice.pojo.enums.GenderType;
import com.socialsync.usersmicroservice.pojo.enums.QueueMessageType;
import com.socialsync.usersmicroservice.pojo.enums.RoleType;
import com.socialsync.usersmicroservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@Service
@EnableScheduling
@Slf4j
public class UsersService implements UsersServiceMethods {
    private UserRepository repository;

    private RabbitMqConnectionFactoryComponent conectionFactory;

    private AmqpTemplate amqpTemplate;

    private Gson gson;

    static List<User> users = List.of(
        new User("timofteIorgu", "myPassword", "timofteIorgu@gmail.com", GenderType.man, RoleType.admin),
                new User("ciocoiuMatei", "imiPlacParoleleComplicate", "ciocoiuMatei@gmail.com", GenderType.man, RoleType.admin),
                new User("tomaIon", "tomaIonPassword1234", "tomaIon@gmail.com", GenderType.man, RoleType.user),
                new User("tomaMaria", "tomaMaria.12CatelusCuParulCret", "tomaMaria@gmail.com", GenderType.woman, RoleType.user),
                new User( "johnDoe", "johnDoe123!Pass", "johnDoe@example.com", GenderType.man, RoleType.user),
                new User("janeSmith", "janeSmith@456", "janeSmith@gmail.com", GenderType.woman, RoleType.user),
                new User("alexJohnson", "alexJohnsonPass", "alexJohnson@yahoo.com", GenderType.woman, RoleType.user),
                new User( "emilyBrown", "emilyBrownPwd", "emilyBrown@hotmail.com", GenderType.woman, RoleType.user),
                new User( "michaelClark", "michaelClarkPass", "michaelClark@gmail.com", GenderType.man, RoleType.user),
                new User( "sarahWhite", "sarahWhitePwd", "sarahWhite@yahoo.com", GenderType.woman, RoleType.user),
                new User("davidTaylor", "davidTaylor123", "davidTaylor@hotmail.com", GenderType.man, RoleType.user),
                new User("oliviaMartin", "oliviaMartinPwd", "oliviaMartin@gmail.com", GenderType.woman, RoleType.user),
                new User("chrisMiller", "chrisMillerPwd", "chrisMiller@yahoo.com", GenderType.man, RoleType.user),
                new User( "amandaYoung", "amandaYoungPass", "amandaYoung@hotmail.com", GenderType.woman, RoleType.user),
                new User( "AnaPopescu", "ParolaAna123!", "ana.popescu@example.com", GenderType.woman, RoleType.user),
                new User( "AndreiIonescu", "IonescuPass456", "andrei.ionescu@example.com", GenderType.man, RoleType.user),
                new User( "ElenaVasilescu", "ElenaPass789", "elena.vasilescu@example.com", GenderType.woman, RoleType.user),
                new User( "MariusDumitrescu", "MariusPass123", "marius.dumitrescu@example.com", GenderType.man, RoleType.user),
                new User("SimonaGheorghiu", "SimonaPwd456", "simona.gheorghiu@example.com", GenderType.woman, RoleType.user),
                new User( "CristianRadu", "Cristian123!", "cristian.radu@example.com", GenderType.man, RoleType.user),
                new User( "AlexandraStanescu", "AlexandraPwd789", "alexandra.stanescu@example.com", GenderType.woman, RoleType.user),
                new User( "AdrianMoldovan", "Adrian456!", "adrian.moldovan@example.com", GenderType.man, RoleType.user)
    );

    public void deleteData() {
        repository.findAll().forEach(x -> deleteUser(x.getId()));
    }

    @Bean
    public void populateDb() {
        deleteData();
        users.forEach(this::addUser);
    }

    @Scheduled(fixedDelay = 30000L)
    public void newRandomUser() {
        log.info("We have " + repository.findAll().size() + " users!");
        if (repository.findAll().size() > 50)
            deleteData();

        User randomUser = users.get(new Random().nextInt(users.size()));
        User newUser = new User();
        newUser.setUsername(randomUser.getUsername() + new Random().nextInt(10000));
        newUser.setPassword(randomUser.getPassword());
        newUser.setEmail(new Random().nextInt(10000) + randomUser.getEmail());
        newUser.setRole(randomUser.getRole());
        newUser.setGender(randomUser.getGender());
        addUser(newUser);
    }

    @Bean
    void initTemplate() {
        this.amqpTemplate = conectionFactory.rabbitTemplate();
    }

    private void sendMessage(UserQueueMessage user) {
        String json = gson.toJson(user);
        this.amqpTemplate.convertAndSend(conectionFactory.getExchange(), conectionFactory.getRoutingKey(), json);
    }

    @Override
    public HashMap<String, UserSelect> fetchAllUsers() {
        HashMap<String, UserSelect> list = new HashMap<>();

        List<User> users =  repository.findAll();

        for (User user : users)
            list.put(user.getId(), new UserSelect(user.getId(),user.getUsername(),user.getEmail(),user.getRole(),user.getGender()));

        return list;
    }

    @Override
    public UserSelect fetchUserById(String id)  throws  RuntimeException {
        //Long id, String username, String email, String role, GenderType gender
        User user =  repository.findById(id).orElseThrow(() -> new RuntimeException("Not found: " + id));
        return  new UserSelect(user.getId(),user.getUsername(),user.getEmail(),user.getRole(),user.getGender());
    }

    @Override
    public void addUser(User user)  {
        repository.save(user);
        sendMessage(new UserQueueMessage(QueueMessageType.CREATE, user));
    }

    @Override
    public void updateUser(String id, User user) throws RuntimeException {
        repository.findById(id).map(elem -> {
            elem.setUsername(user.getUsername());
            elem.setEmail(user.getEmail());
            elem.setGender(user.getGender());
            elem.setPassword(user.getPassword());
            repository.save(elem);
            sendMessage(new UserQueueMessage(QueueMessageType.UPDATE, user));
            return elem;
        }).orElseThrow(() -> {
            repository.save(user);
            sendMessage(new UserQueueMessage(QueueMessageType.CREATE, user));
            return new RuntimeException("User not found. Created one instead.");
        });
    }

    @Override
    public void deleteUser(String id) throws RuntimeException {
        Optional<User> user = repository.findById(id);

        if (user.isPresent()) {
            repository.deleteById(id);
            sendMessage(new UserQueueMessage(QueueMessageType.DELETE, user.get()));
        }
        else
            throw new RuntimeException("Created one instead.");
    }

}
