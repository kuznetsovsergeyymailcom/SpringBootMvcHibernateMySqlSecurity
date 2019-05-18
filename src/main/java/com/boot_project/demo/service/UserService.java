package com.boot_project.demo.service;


import com.boot_project.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserByName(String name);
    User getUserById(Long id);
    User getUserByEmail(String email);

    void addUser(User user);
    void update(User user);
    void remove(Long id);
}
