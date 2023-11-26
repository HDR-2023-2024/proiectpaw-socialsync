package com.socialsync.usersmicroservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialsync.usersmicroservice.pojo.User;
import com.socialsync.usersmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.util.List;

@Component
public class ClientRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    String jsonString = "[\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"timofteIorgu\",\n" +
            "    \"password\": \"myPassword\",\n" +
            "    \"email\": \"timofteIorgu@gmail.com\",\n" +
            "    \"gender\": \"man\",\n" +
            "    \"role\": \"admin\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"ciocoiuMatei\",\n" +
            "    \"password\": \"imiPlacParoleleComplicate\",\n" +
            "    \"email\": \"ciocoiuMatei@gmail.com\",\n" +
            "    \"gender\": \"man\",\n" +
            "    \"role\": \"admin\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"tomaIon\",\n" +
            "    \"password\": \"tomaIonPassword1234\",\n" +
            "    \"email\": \"tomaIon@gmail.com\",\n" +
            "    \"gender\": \"man\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"tomaMaria\",\n" +
            "    \"password\": \"tomaMaria.12CatelusCuParulCret\",\n" +
            "    \"email\": \"tomaMaria@gmail.com\",\n" +
            "    \"gender\": \"woman\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"johnDoe\",\n" +
            "    \"password\": \"johnDoe123!Pass\",\n" +
            "    \"email\": \"johnDoe@example.com\",\n" +
            "    \"gender\": \"man\",\n" +
            "    \"role\": \"user\"\n" +
            "  },{\n" +
            "  \"id\": null,\n" +
            "  \"username\": \"janeSmith\",\n" +
            "  \"password\": \"janeSmith@456\",\n" +
            "  \"email\": \"janeSmith@gmail.com\",\n" +
            "  \"gender\": \"woman\",\n" +
            "  \"role\": \"user\"\n" +
            "},\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"alexJohnson\",\n" +
            "    \"password\": \"alexJohnsonPass\",\n" +
            "    \"email\": \"alexJohnson@yahoo.com\",\n" +
            "    \"gender\": \"man\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"emilyBrown\",\n" +
            "    \"password\": \"emilyBrownPwd\",\n" +
            "    \"email\": \"emilyBrown@hotmail.com\",\n" +
            "    \"gender\": \"woman\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"michaelClark\",\n" +
            "    \"password\": \"michaelClarkPass\",\n" +
            "    \"email\": \"michaelClark@gmail.com\",\n" +
            "    \"gender\": \"man\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"sarahWhite\",\n" +
            "    \"password\": \"sarahWhitePwd\",\n" +
            "    \"email\": \"sarahWhite@yahoo.com\",\n" +
            "    \"gender\": \"woman\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"davidTaylor\",\n" +
            "    \"password\": \"davidTaylor123\",\n" +
            "    \"email\": \"davidTaylor@hotmail.com\",\n" +
            "    \"gender\": \"man\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"oliviaMartin\",\n" +
            "    \"password\": \"oliviaMartinPwd\",\n" +
            "    \"email\": \"oliviaMartin@gmail.com\",\n" +
            "    \"gender\": \"woman\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"chrisMiller\",\n" +
            "    \"password\": \"chrisMillerPwd\",\n" +
            "    \"email\": \"chrisMiller@yahoo.com\",\n" +
            "    \"gender\": \"man\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"amandaYoung\",\n" +
            "    \"password\": \"amandaYoungPass\",\n" +
            "    \"email\": \"amandaYoung@hotmail.com\",\n" +
            "    \"gender\": \"woman\",\n" +
            "    \"role\": \"user\"\n" +
            "  },{\n" +
            "  \"id\": null,\n" +
            "  \"username\": \"AnaPopescu\",\n" +
            "  \"password\": \"ParolaAna123!\",\n" +
            "  \"email\": \"ana.popescu@example.com\",\n" +
            "  \"gender\": \"woman\",\n" +
            "  \"role\": \"user\"\n" +
            "},\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"AndreiIonescu\",\n" +
            "    \"password\": \"IonescuPass456\",\n" +
            "    \"email\": \"andrei.ionescu@example.com\",\n" +
            "    \"gender\": \"man\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"ElenaVasilescu\",\n" +
            "    \"password\": \"ElenaPass789\",\n" +
            "    \"email\": \"elena.vasilescu@example.com\",\n" +
            "    \"gender\": \"woman\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"MariusDumitrescu\",\n" +
            "    \"password\": \"MariusPass123\",\n" +
            "    \"email\": \"marius1.dumitrescu@example.com\",\n" +
            "    \"gender\": \"man\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"SimonaGheorghiu\",\n" +
            "    \"password\": \"SimonaPwd456\",\n" +
            "    \"email\": \"simona.gheorghiu@example.com\",\n" +
            "    \"gender\": \"woman\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"CristianRadu\",\n" +
            "    \"password\": \"Cristian123!\",\n" +
            "    \"email\": \"cristian.radu@example.com\",\n" +
            "    \"gender\": \"man\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"AlexandraStanescu\",\n" +
            "    \"password\": \"AlexandraPwd789\",\n" +
            "    \"email\": \"alexandra.stanescu@example.com\",\n" +
            "    \"gender\": \"woman\",\n" +
            "    \"role\": \"user\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"username\": \"AdrianMoldovan\",\n" +
            "    \"password\": \"Adrian456!\",\n" +
            "    \"email\": \"adrian.moldovan@example.com\",\n" +
            "    \"gender\": \"man\",\n" +
            "    \"role\": \"user\"\n" +
            "  }\n" +
            "]";
    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        if(userRepository.findAll().isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();

            User[] users = objectMapper.readValue(jsonString, User[].class);

            for (User user : users) {
                userRepository.save(user);
            }
        }
    }
}