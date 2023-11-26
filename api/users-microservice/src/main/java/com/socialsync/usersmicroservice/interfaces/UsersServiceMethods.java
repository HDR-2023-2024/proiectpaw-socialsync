package com.socialsync.usersmicroservice.interfaces;

import com.socialsync.usersmicroservice.pojo.User;
import com.socialsync.usersmicroservice.pojo.User;
import com.socialsync.usersmicroservice.pojo.UserSelect;

import java.util.HashMap;

public interface UsersServiceMethods {
    HashMap<String, UserSelect> fetchAllUsers();
    UserSelect fetchUserById(String id) throws  RuntimeException;
    void addUser(User user);
    void updateUser(String id, User user) throws  RuntimeException;
    void deleteUser(String id) throws  RuntimeException;
}
