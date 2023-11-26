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

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findAll().isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("input.json");
            List<User> userList = objectMapper.readValue(file, new TypeReference<List<User>>() {});
            for (User user : userList) {
                userRepository.save(user);
                System.out.println(user);
            }
        }
    }
}