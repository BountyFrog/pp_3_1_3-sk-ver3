package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.*;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User findOne(int id) {
        Optional<User> person = userRepository.findById(id);
        return person.orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

//    public boolean add(User user) {
//        User userFromDB = userRepository.findByUsername(user.getUsername());
//        if (userFromDB != null) {
//            return false;
//        }
//        setRoleDefault(user);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//        return true;
//    }
//
//    public void setRoleDefault(User user) {
//        user.getRoles().add(roleRepository.findByName("ROLE_USER").orElse(null));
//    }
}