package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.authentication.LoginRequest;
import hu.wv.MonkeSwapBackend.authentication.AuthenticationResponse;
import hu.wv.MonkeSwapBackend.authentication.RegisterRequest;
import hu.wv.MonkeSwapBackend.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public void register(
            @RequestBody RegisterRequest request
    ) {
        service.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(service.login(request));
    }
}
