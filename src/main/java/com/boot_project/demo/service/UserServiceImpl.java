package com.boot_project.demo.service;

import com.boot_project.demo.model.User;
import com.boot_project.demo.repository.RoleRepository;
import com.boot_project.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User getUserByName(String name) {

        return userRepository.getUserByName(name);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void addUser(User user) {

        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.saveAndFlush(user);

    }

    @Override
    public void remove(Long id) {
        User user = userRepository.findById(id).get();

        userRepository.delete(user);
    }
}
