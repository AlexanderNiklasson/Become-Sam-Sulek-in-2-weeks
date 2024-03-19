package com.backend.becomeSamSulek.service;

import com.backend.becomeSamSulek.model.User;
import com.backend.becomeSamSulek.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getOneUser(int id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));
    }

    public User addUser(User user) {
        User userTemp = userRepository.getOneByUsername(user.getUsername());
        if(userTemp != null) {
            throw new HttpStatusCodeException(HttpStatus.BAD_REQUEST, "Username already exists") {};
        }
        userRepository.save(user);
        return user;
    }

    public User updateUser(int id, User user) {
        User userTemp = userRepository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist"));
        userTemp.setDuration(user.getDuration());
        userTemp.setUsername(user.getUsername());
        userTemp.setLevel(user.getLevel());
        userTemp.setType(user.getType());
        userRepository.save(userTemp);
        return userTemp;
    }

}
