package com.example.TradeTech.Service;

import com.example.TradeTech.Model.User;
import com.example.TradeTech.Repository.UserRepository;
import com.example.TradeTech.dto.LoginDTO;
import com.example.TradeTech.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User register(RegisterDTO dto) {
        User user = new User();
        user.setName(dto.name);
        user.setEmail(dto.email);
        user.setPassword(dto.password); // hash later
        return userRepository.save(user);
    }

    public User login(LoginDTO dto) {
        User user = userRepository.findByEmail(dto.email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(dto.password)) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
