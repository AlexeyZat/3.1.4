package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Configuration
public class inDataBaseInitializer {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public inDataBaseInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        roleService.save(adminRole);
        roleService.save(userRole);

        User admin = new User("admin", "admin", "admin");
        admin.setLastName("Adminov");
        admin.setEmail("admin@mail.ru");
        admin.setAge(35);
        admin.setRoles(Set.of(adminRole, userRole));
        userService.saveUser(admin);

        User user = new User("user", "user", "user");
        user.setLastName("Userov");
        user.setEmail("user@mail.ru");
        user.setAge(30);
        user.setRoles(Set.of(userRole));
        userService.saveUser(user);
    }
}

