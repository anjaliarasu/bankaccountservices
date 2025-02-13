package com.marlow.accountservice.serviceTest;

import com.marlow.accountservice.entity.User;
import com.marlow.accountservice.repositoryTest.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service("UserService")
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    //Service to create User if user with email doesnt exist
    public User saveUser(User user) {
        User newUser = null;
        try {
            //check if user with email id already exist
            if (userRepository.findByEmail(user.getEmail()) == null) {
                 newUser = User.builder().name(user.getName()).email(user.getEmail()).build();

                User user1=userRepository.save(newUser);
                log.info("User saved successfullyy");
                return user1;

            }

        } catch (Exception e) {
            log.error("User creation failed");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Failed to save User");
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT,"Fail: Email already exists!");
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findByName(String title) {
        return userRepository.findByName(title);
    }

    public User sfindByEmail(String title) {
        return userRepository.findByEmail(title);
    }

}
