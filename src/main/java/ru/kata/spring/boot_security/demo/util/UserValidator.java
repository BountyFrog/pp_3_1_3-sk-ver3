package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

@Component
public class UserValidator implements Validator {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public UserValidator(UserDetailsServiceImpl userDetailsServiceLmpl) {
        this.userDetailsServiceImpl = userDetailsServiceLmpl;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User person = (User) target;
        try {
            userDetailsServiceImpl.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException e) {
            return;
        }
        errors.rejectValue("username", "", "Пользователь уже существует");
    }
}