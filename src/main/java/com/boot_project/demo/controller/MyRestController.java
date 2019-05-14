package com.boot_project.demo.controller;

import com.boot_project.demo.model.User;
import com.boot_project.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class MyRestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok().body(allUsers);
    }

    @GetMapping(value = "/get-user-by-id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/get-user-by-name/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable("name") String name){
        User userByName = userService.getUserByName(name);
        return ResponseEntity.ok().body(userByName);
    }

    @PostMapping(value = "/save-user")
    public ResponseEntity addUser(@RequestBody User user){
        userService.addUser(user);
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok().body(allUsers);
    }

    @PutMapping(value = "/update-user/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody User user){
        user.setId(id);
        userService.addUser(user);
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok().body(allUsers);
    }


    @DeleteMapping(value = "/delete-user/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        userService.remove(id);
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok().body(allUsers);
    }

}
