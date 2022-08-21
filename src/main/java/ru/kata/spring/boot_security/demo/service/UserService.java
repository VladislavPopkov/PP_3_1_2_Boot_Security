package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Collections;
import java.util.List;


@Service
@PropertySource("classpath:application.properties")
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByName(String username) {
        return userRepository.findByFirstName(username);
    }

    public User save(User user) {
        user.setRoles(Collections.singleton(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    public User update (User user) {
        return userRepository.save(user);
    }

    public List<User> userList() {
        return userRepository.findAll();
    }

    public void delete(Long id) {
        User user = userRepository.getById(id);
        userRepository.delete(user);
    }

    public User findById(Long id){
        return userRepository.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByFirstName(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found ", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getFirstName(), user.getPassword(), user.getAuthorities());
    }
}
