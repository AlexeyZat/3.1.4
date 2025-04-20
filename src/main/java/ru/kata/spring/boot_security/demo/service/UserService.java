package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;

@Service
public interface UserService {

    List<User> getAllUsers();

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

    User getUserById(int id);

    User findByUsername(String username);

    Object getAllRoles();
}

