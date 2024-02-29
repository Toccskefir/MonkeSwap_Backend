package hu.wv.MonkeSwapBackend.services;

import hu.wv.MonkeSwapBackend.authentication.AuthenticationResponse;
import hu.wv.MonkeSwapBackend.authentication.LoginRequest;
import hu.wv.MonkeSwapBackend.authentication.RegisterRequest;
import hu.wv.MonkeSwapBackend.enums.UserRole;
import hu.wv.MonkeSwapBackend.exceptions.IsEmptyException;
import hu.wv.MonkeSwapBackend.exceptions.IsRegisteredException;
import hu.wv.MonkeSwapBackend.model.User;
import hu.wv.MonkeSwapBackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new IsRegisteredException("Email is already registered");
        }
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new IsRegisteredException("Username is taken");
        }
        if (request.getEmail().isBlank()) {
            throw new IsEmptyException("Email");
        }
        if (request.getUsername().isBlank()) {
            throw new IsEmptyException("Username");
        }
        if (request.getPassword().isBlank()) {
            throw new IsEmptyException("Password");
        }
        if (!Pattern.matches("^.+@.+\\..+$", request.getEmail())) {
            throw new IllegalArgumentException("Email is in wrong format");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .tradesCompleted(0)
                .dateOfRegistration(LocalDate.now().toString())
                .build();
        repository.save(user);
    }

    public AuthenticationResponse login(LoginRequest request) {
        if (request.getEmail().isBlank()) {
            throw new IsEmptyException("Email");
        }
        if (request.getPassword().isBlank()) {
            throw new IsEmptyException("Password");
        }

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        User user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new BadCredentialsException("Wrong email or password"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
