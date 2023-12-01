package com.socialsync.usersmicroservice.interfaces;

import com.socialsync.usersmicroservice.pojo.Credentials;
import com.socialsync.usersmicroservice.pojo.User;
import com.socialsync.usersmicroservice.pojo.User;
import com.socialsync.usersmicroservice.pojo.UserSelect;

import java.util.HashMap;

public interface UsersServiceMethods {
    HashMap<String, UserSelect> fetchAllUsers();
    UserSelect fetchUserById(String id) throws  RuntimeException;
    void addUser(User user);
    void updateUser(String id, UserSelect user) throws  RuntimeException;
    void updatePassword(String id, String password) throws  RuntimeException;
    void deleteUser(String id) throws  RuntimeException;
    User login(Credentials credentials) throws  RuntimeException;
    String isValidJWT(String jwt) throws Exception;
    String generateJWT(String id);
}
