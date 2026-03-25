package com.mastermind.game.service;

import com.mastermind.game.config.JwtService;
import com.mastermind.game.dto.*;
import com.mastermind.game.entity.User;
import com.mastermind.game.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public UserService(UserRepository repository, PasswordEncoder encoder, JwtService jwtService) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public UserResponseDTO register(RegisterRequestDTO request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));

        User saved = repository.save(user);

        return new UserResponseDTO(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail()
        );
    }

    public AuthResponseDTO login(String login, String password) {

        User user = repository.findByEmail(login)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtService.generateToken(user.getEmail());

        UserResponseDTO userDTO = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );

        return new AuthResponseDTO(userDTO, token);
    }
}