package com.example.TradeTech.Service;

import com.example.TradeTech.Exceptions.InvalidCredentialException;
import com.example.TradeTech.Exceptions.ResourceNotFoundException;
import com.example.TradeTech.Model.User;
import com.example.TradeTech.Repository.UserRepository;
import com.example.TradeTech.dto.LoginDTO;
import com.example.TradeTech.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User register(RegisterDTO dto) {
        try{
            User user = new User();
            user.setName(dto.name);
            user.setEmail(dto.email);
            user.setPassword(dto.password); // hash later
            return userRepository.save(user);
        } catch(Exception e) {
            throw new RuntimeException("Registration failed",e);
        }

    }

    public User login(LoginDTO dto) {
        try {
            User user = userRepository.findByEmail(dto.email)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            if (!user.getPassword().equals(dto.password)) {
                throw new InvalidCredentialException("Invalid password");
            }

            return user;
        } catch (Exception e) {
            throw new RuntimeException("Login failed",e);
        }
    }


    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch users",e);
        }
    }

    public User getUserById(Long id) {
        try {
            return userRepository.findById(id)
                    .orElseThrow(() ->new ResourceNotFoundException("User not found with id: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch user",e);
        }
    }

    public void deleteUser(Long id) {
        try {
            if(!userRepository.existsById(id)) {
                throw new ResourceNotFoundException("User not found with id: " + id);
            }
            userRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to delete user",e);
        }
    }

    public User updateUser(Long id,RegisterDTO dto) {
        try {
            User existing = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("user is not found with id: " + id));

            existing.setName(dto.name);
            existing.setEmail(dto.email);
            existing.setPassword(dto.password);
            return userRepository.save(existing);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user",e);
        }
    }
}
