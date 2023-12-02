package com.socialsync.usersmicroservice.interfaces;

import com.socialsync.usersmicroservice.exceptions.NotAcceptableException;
import com.socialsync.usersmicroservice.exceptions.NotFoundException;
import com.socialsync.usersmicroservice.pojo.*;
import com.socialsync.usersmicroservice.pojo.User;

import java.util.HashMap;

public interface UsersServiceMethods {
    HashMap<String, UserSelect> fetchAllUsers();
    UserSelect fetchUserById(String id) throws  RuntimeException;
    void addUser(User user) throws NotAcceptableException;
    void updateUser(String id, UserSelect user) throws RuntimeException, NotFoundException, NotAcceptableException;
    void updatePassword(String id, String password) throws  RuntimeException;
    void deleteUser(String id) throws  RuntimeException;
    AuthorizedInfo login(Credentials credentials) throws  RuntimeException;
    AuthorizedInfo isValidJWT(String jwt) throws Exception;
    String generateJWT(String id);
}
