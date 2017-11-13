package com.ofn.dao.interfaces;

import com.ofn.model.User;

import java.util.List;

public interface UserDao {

    User getUserById(int userId);
    User getUserByName(String userName);
    List<User> getAllUsers();
    User addUser(User user);
    boolean updateUser(User user);
    boolean removeUser(int userID);
    List<User> searchUsers(String... args);
}