package com.generation.luminar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.luminar.model.UserLogin;
import com.generation.luminar.model.User;
import com.generation.luminar.repository.UserRepository;
import com.generation.luminar.security.JwtService;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<User> singUser(User user) {

        if (userRepository.findByName(user.getEmail()).isPresent())
            return Optional.empty();

        user.setPassword(encryptPassword(user.getPassword()));

        return Optional.of(userRepository.save(user));

    }

    public Optional<User> updateUser(User user) {

        if(userRepository.findById(user.getId()).isPresent()) {

            Optional<User> findUser = userRepository.findByName(user.getEmail());

            if ( (findUser.isPresent()) && ( findUser.get().getId() != user.getId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

            user.setPassword(encryptPassword(user.getPassword()));

            return Optional.ofNullable(userRepository.save(user));

        }

        return Optional.empty();

    }

    public Optional<UserLogin> authenticateUser (Optional<UserLogin> userLogin) {

        var credentials = new UsernamePasswordAuthenticationToken(userLogin.get().getEmail(),userLogin.get().getPassword());

        Authentication authentication = authenticationManager.authenticate(credentials);

        if (authentication.isAuthenticated()) {

            Optional<User> user = userRepository.findByName(userLogin.get().getEmail());

            if (user.isPresent()) {

                userLogin.get().setId(user.get().getId());
                userLogin.get().setName(user.get().getEmail());
                userLogin.get().setPhoto(user.get().getPhoto());
                userLogin.get().setToken(generateToken(userLogin.get().getEmail()));
                userLogin.get().setPassword("");

                return userLogin;
            }
        }
        return Optional.empty();
    }

    private String encryptPassword(String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    private String generateToken(String user) {
        return "Bearer " + jwtService.generateToken(user);
    }

}
