package com.socialsync.usersmicroservice.service;

import com.socialsync.usersmicroservice.interfaces.UsersServiceMethods;
import com.socialsync.usersmicroservice.pojo.User;
import com.socialsync.usersmicroservice.pojo.UserSelect;
import com.socialsync.usersmicroservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Service
public class UsersService implements UsersServiceMethods {
    private UserRepository repository;

    @Override
    public HashMap<String, UserSelect> fetchAllUsers() {
        HashMap<String, UserSelect> list = new HashMap<>();

        List<User> users =  repository.findAll();

        for (User user : users)
            list.put(user.getId(), new UserSelect(user.getId(),user.getUsername(),user.getEmail(),user.getRole(),user.getGender()));

        return list;
    }

    @Override
    public UserSelect fetchUserById(String id)  throws  Exception {
        //Long id, String username, String email, String role, GenderType gender
        User user =  repository.findById(id).orElseThrow(() -> new Exception("Not found: " + id));
        return  new UserSelect(user.getId(),user.getUsername(),user.getEmail(),user.getRole(),user.getGender());
    }

    @Override
    public void addUser(User user)  {
        repository.save(user);
    }

    @Override
    public void updateUser(String id, User user) throws Exception {
        repository.findById(id).map(elem -> {
            elem.setUsername(user.getUsername());
            elem.setEmail(user.getEmail());
            elem.setGender(user.getGender());
            elem.setPassword(user.getPassword());
            repository.save(elem);
            return elem;
        }).orElseThrow(() -> {
            repository.save(user);
            return new Exception("User not found. Created one instead.");
        });
    }

    @Override
    public void deleteUser(String id) {
        repository.deleteById(id);
    }
}
