package com.generation.luminar.service;

import com.generation.luminar.model.User;
import com.generation.luminar.model.UserLogin;
import com.generation.luminar.repository.UserRepository;
import com.generation.luminar.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;

import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<User> registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent())
            return Optional.empty();

        user.setPassword(encodePassword(user.getPassword()));

        return Optional.of(userRepository.save(user));

    }

    public Optional<User> upadateUser(User user) {

        if(userRepository.findById(user.getId()).isPresent()) {

            Optional<User> findUser = userRepository.findByEmail(user.getEmail());

            if ( (findUser.isPresent()) && ( findUser.get().getId() != user.getId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered!", null);

            user.setPassword(encodePassword(user.getPassword()));

            return Optional.ofNullable(userRepository.save(user));

        }

        return Optional.empty();

    }

    public Optional<UserLogin> authenticateUser(Optional<UserLogin> userLogin) {

        // Gera o Objeto de autenticação
        var credenciais = new UsernamePasswordAuthenticationToken(userLogin.get().getEmail(), userLogin.get().getPassword());

        // Autentica o Usuario
        Authentication authentication = authenticationManager.authenticate(credenciais);

        // Se a autenticação foi efetuada com sucesso
        if (authentication.isAuthenticated()) {

            // Busca os dados do usuário
            Optional<User> user = userRepository.findByEmail(userLogin.get().getEmail());

            // Se o usuário foi encontrado
            if (user.isPresent()) {

                // Preenche o Objeto usuarioLogin com os dados encontrados
                userLogin.get().setId(user.get().getId());
                userLogin.get().setName(user.get().getName());
                userLogin.get().setPhoto(user.get().getPhoto());
                userLogin.get().setToken(generateToken(userLogin.get().getEmail()));
                userLogin.get().setPassword("");

                // Retorna o Objeto preenchido
                return userLogin;

            }

        }

        return Optional.empty();

    }

    private String encodePassword(String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);

    }

    private String generateToken(String email) {
        return "Bearer " + jwtService.generateToken(email);
    }


}
