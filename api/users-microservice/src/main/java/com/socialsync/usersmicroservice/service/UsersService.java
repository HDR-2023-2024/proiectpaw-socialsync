package com.socialsync.usersmicroservice.service;

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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import io.jsonwebtoken.Claims;

@AllArgsConstructor
@Service
@EnableScheduling
@Slf4j
public class UsersService implements UsersServiceMethods {
    private UserRepository repository;
    private JWTService jwtService;

    private RabbitMqConnectionFactoryComponent conectionFactory;
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);
    private AmqpTemplate amqpTemplate;

    private Gson gson;

    static List<User> users = List.of(
            new User("timofteIorgu", "myPassword", "timofteIorgu@gmail.com", GenderType.man, RoleType.admin),
            new User("ciocoiuMatei", "imiPlacParoleleComplicate", "ciocoiuMatei@gmail.com", GenderType.man, RoleType.admin),
            new User("tomaIon", "tomaIonPassword1234", "tomaIon@gmail.com", GenderType.man, RoleType.user),
            new User("tomaMaria", "tomaMaria.12CatelusCuParulCret", "tomaMaria@gmail.com", GenderType.woman, RoleType.user),
            new User("johnDoe", "johnDoe123!Pass", "johnDoe@example.com", GenderType.man, RoleType.user),
            new User("janeSmith", "janeSmith@456", "janeSmith@gmail.com", GenderType.woman, RoleType.user),
            new User("alexJohnson", "alexJohnsonPass", "alexJohnson@yahoo.com", GenderType.woman, RoleType.user),
            new User("emilyBrown", "emilyBrownPwd", "emilyBrown@hotmail.com", GenderType.woman, RoleType.user),
            new User("michaelClark", "michaelClarkPass", "michaelClark@gmail.com", GenderType.man, RoleType.user),
            new User("sarahWhite", "sarahWhitePwd", "sarahWhite@yahoo.com", GenderType.woman, RoleType.user),
            new User("davidTaylor", "davidTaylor123", "davidTaylor@hotmail.com", GenderType.man, RoleType.user),
            new User("oliviaMartin", "oliviaMartinPwd", "oliviaMartin@gmail.com", GenderType.woman, RoleType.user),
            new User("chrisMiller", "chrisMillerPwd", "chrisMiller@yahoo.com", GenderType.man, RoleType.user),
            new User("amandaYoung", "amandaYoungPass", "amandaYoung@hotmail.com", GenderType.woman, RoleType.user),
            new User("AnaPopescu", "ParolaAna123!", "ana.popescu@example.com", GenderType.woman, RoleType.user),
            new User("AndreiIonescu", "IonescuPass456", "andrei.ionescu@example.com", GenderType.man, RoleType.user),
            new User("ElenaVasilescu", "ElenaPass789", "elena.vasilescu@example.com", GenderType.woman, RoleType.user),
            new User("MariusDumitrescu", "MariusPass123", "marius.dumitrescu@example.com", GenderType.man, RoleType.user),
            new User("SimonaGheorghiu", "SimonaPwd456", "simona.gheorghiu@example.com", GenderType.woman, RoleType.user),
            new User("CristianRadu", "Cristian123!", "cristian.radu@example.com", GenderType.man, RoleType.user),
            new User("AlexandraStanescu", "AlexandraPwd789", "alexandra.stanescu@example.com", GenderType.woman, RoleType.user),
            new User("AdrianMoldovan", "Adrian456!", "adrian.moldovan@example.com", GenderType.man, RoleType.user)
    );

    public void deleteData() {
        repository.findAll().forEach(x -> deleteUser(x.getId()));
    }

    @Bean
    public void populateDb() throws NotAcceptableException {
        deleteData();
        for (User u : users
             ) {
            addUser(u);
        }
    }

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Scheduled(fixedDelay = 30000L)
    public void newRandomUser() throws NotAcceptableException {
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
        List<User> users = repository.findAll();
        for (User user : users)
            list.put(user.getId(), new UserSelect(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getGender()));
        return list;
    }

    @Override
    public UserSelect fetchUserById(String id) throws RuntimeException {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found: " + id));
        return new UserSelect(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getGender());
    }

    @Override
    public void addUser(User user) throws NotAcceptableException {
        user.setPassword(this.passwordEncoder().encode(user.getPassword()));
        try {
            repository.save(user);
            sendMessage(new UserQueueMessage(QueueMessageType.CREATE, user));
        } catch (Exception ex) {
            if (ex.getMessage().contains("uk_email")) {
                throw new NotAcceptableException("There is already a user with this email.");
            } else if (ex.getMessage().contains("uk_username")) {
                throw new NotAcceptableException("There is already a user with this username.");
            }
        }
    }

    @Override
    public void updateUser(String id, UserSelect user) throws RuntimeException, NotFoundException, NotAcceptableException {
        Optional<User> userInDb = repository.findById(id);
        if (userInDb.isPresent()) {
            try {
                repository.updateUser(id, user.getUsername(), user.getEmail(), user.getRole(), user.getGender());
                sendMessage(new UserQueueMessage(QueueMessageType.UPDATE, new User(id, user.getUsername(), null, user.getEmail(), user.getGender(), user.getRole())));
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

    @Override
    public AuthorizedInfo isValidJWT(String jwt) throws Exception {
        try {
            String id = jwtService.getIdFromToken(jwt);
            Optional<User> user = repository.findById(id);
            if (user.isPresent()) {
                return new AuthorizedInfo(id, user.get().getRole().name());
            }
            throw new UnauthorizedException();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new UnauthorizedException();
        }
    }

    @Override
    public String generateJWT(String id) {
        return jwtService.generateAccessToken(id);
    }

}
