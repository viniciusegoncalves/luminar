package com.generation.luminar.controller;

import com.generation.luminar.model.User;
import com.generation.luminar.model.UserLogin;
import com.generation.luminar.repository.UserRepository;
import com.generation.luminar.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<User> postUser(@RequestBody @Valid User user) {

        return userService.registerUser(user)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

    }

    @PostMapping("/login")
    public ResponseEntity<UserLogin> authenticateUser(@RequestBody Optional<UserLogin> userLogin){

        return userService.authenticateUser(userLogin)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }


    @PutMapping("/update")
    public ResponseEntity<User> putUser(@Valid @RequestBody User user) {

        return userService.upadateUser(user)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity <List<User>> getAll(){

        return ResponseEntity.ok(userRepository.findAll());
    }
}
