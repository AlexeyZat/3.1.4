package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository , @Lazy PasswordEncoder passwordEncoder, RoleRepository roleRepository ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllUsers() {
       return userRepository.findAll();
    }


    @Override
    @Transactional
    public void saveUser(User user) {
       // if (!user.getPassword().matches("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
       // }
        user.setRoles(resolveRoles(user.getRoles()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        // if (!user.getPassword().matches("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
       // }
        user.setRoles(resolveRoles(user.getRoles()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);

    }

    @Override
    public Object getAllRoles() {
        return null;
    }

    private Set<Role> resolveRoles(Set<Role> roles) {
        return roles.stream()
                .map(role -> roleRepository.findById(role.getId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}

