package com.boot_project.demo.controller;

import com.boot_project.demo.model.Role;
import com.boot_project.demo.model.User;
import com.boot_project.demo.service.RoleService;
import com.boot_project.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest")
public class MyRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

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

    @GetMapping(value = "/get-role-by-id/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") Long id){
        Role role = roleService.getRoleById(id);
        return ResponseEntity.ok().body(role);
    }

    @GetMapping(value = "/get-user-by-name/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable("name") String name){
        User userByName = userService.getUserByName(name);
        return ResponseEntity.ok().body(userByName);
    }

    @GetMapping(value = "/get-user-by-email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email){
        User userByName = userService.getUserByEmail(email);
        return ResponseEntity.ok().body(userByName);
    }


    @PostMapping(value = "/save-user")
    public ResponseEntity addUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/update-user")
    public ResponseEntity update(@RequestBody User user){
        userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @DeleteMapping(value = "/delete-user/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        userService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
