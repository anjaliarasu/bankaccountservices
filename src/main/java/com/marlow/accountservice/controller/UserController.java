package com.marlow.accountservice.controller;


import com.marlow.accountservice.entity.User;
import com.marlow.accountservice.serviceTest.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // create a user
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        User newUser=userService.saveUser(user);
        Map<String, String> body = new HashMap<>();
        if(newUser!=null)
        {
            body.put("message", "Success: User details successfully saved!");
            return new ResponseEntity<>(body,HttpStatus.CREATED);
        }
        else {
            body.put("message", "Fail: Email already exists!");
            return new ResponseEntity<>(body, HttpStatus.CONFLICT);
        }


    }

    // update a user
    @PutMapping
    public User update(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // delete a user
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/find/name/{name}")
    public List<User> findByName(@PathVariable String title) {
        return userService.findByName(title);
    }


}

