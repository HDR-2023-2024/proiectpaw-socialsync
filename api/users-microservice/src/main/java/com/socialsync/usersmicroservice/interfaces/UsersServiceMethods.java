package com.socialsync.usersmicroservice.interfaces;

import com.socialsync.usersmicroservice.pojo.User;
import com.socialsync.usersmicroservice.pojo.User;
import com.socialsync.usersmicroservice.pojo.UserSelect;

import java.util.HashMap;

public interface UsersServiceMethods {
    HashMap<Long, UserSelect> fetchAllUsers();
    UserSelect fetchUserById(Long id) throws  Exception;
    void addUser(User user);
    void updateUser(Long id, User user) throws  Exception;
    void deleteUser(Long id) throws  Exception;
}
