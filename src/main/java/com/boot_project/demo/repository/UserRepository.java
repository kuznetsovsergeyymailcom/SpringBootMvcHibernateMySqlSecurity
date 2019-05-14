package com.boot_project.demo.repository;

import com.boot_project.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.username = :name")
    User getUserByName(String name);
}
