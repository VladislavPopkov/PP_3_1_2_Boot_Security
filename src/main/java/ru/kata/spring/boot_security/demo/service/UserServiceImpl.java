package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


@Service
@PropertySource("classpath:application.properties")
public class UserServiceImpl implements UserDetailsService, UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByName(String username) {
        return userRepository.findByName(username);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found ", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), user.getAuthorities());
    }
}
