package com.financeadvicechatbot.service;

import com.financeadvicechatbot.model.User;
import com.financeadvicechatbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    // Making the userService
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Finding a user by their email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Function to register a new user to the site
    public User registerUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }
}
