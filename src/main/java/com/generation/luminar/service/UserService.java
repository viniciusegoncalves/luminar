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

        if (userRepository.findByUser(user.getName()).isPresent())
            return Optional.empty();

        user.setPassword(encryptPassword(user.getPassword()));

        return Optional.of(userRepository.save(user));

    }

    public Optional<User> updateUser(User user) {

        if(userRepository.findById(user.getId()).isPresent()) {

            Optional<User> findUser = userRepository.findByUser(user.getName());

            if ( (findUser.isPresent()) && ( findUser.get().getId() != user.getId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

            user.setPassword(encryptPassword(user.getPassword()));

            return Optional.ofNullable(userRepository.save(user));

        }

        return Optional.empty();

    }

    public Optional<UserLogin> authenticateUser (Optional<UserLogin> userLogin) {

        // Gera o Objeto de autenticação
        var credentials = new UsernamePasswordAuthenticationToken(userLogin.get().getName(),userLogin.get().getPassword());

        // Autentica o user
        Authentication authentication = authenticationManager.authenticate(credentials);

        // Se a autenticação foi efetuada com sucesso
        if (authentication.isAuthenticated()) {

            // Busca os dados do usuário
            Optional<User> user = userRepository.findByUser(userLogin.get().getName());

            // Se o usuário foi encontrado
            if (user.isPresent()) {

                // Preenche o Objeto usuarioLogin com os dados encontrados
                userLogin.get().setId(user.get().getId());
                userLogin.get().setName(user.get().getName());
                userLogin.get().setPhoto(user.get().getPhoto());
                userLogin.get().setToken(generateToken(userLogin.get().getName()));
                userLogin.get().setPassword("");

                // Retorna o Objeto preenchido
                return userLogin;

            }

        }

        return Optional.empty();

    }

    private String encryptPassword(String senha) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(senha);

    }

    private String generateToken(String user) {
        return "Bearer " + jwtService.generateToken(user);
    }

}
