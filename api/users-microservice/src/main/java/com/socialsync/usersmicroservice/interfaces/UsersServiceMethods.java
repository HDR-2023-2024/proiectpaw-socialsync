package com.socialsync.usersmicroservice.interfaces;

import com.socialsync.usersmicroservice.pojo.User;
import com.socialsync.usersmicroservice.pojo.User;
import com.socialsync.usersmicroservice.pojo.UserSelect;

import java.util.HashMap;

public interface UsersServiceMethods {
    HashMap<String, UserSelect> fetchAllUsers();
    UserSelect fetchUserById(String id) throws  Exception;
    void addUser(User user);
    void updateUser(String id, User user) throws  Exception;
    void deleteUser(String id) throws  Exception;
}
