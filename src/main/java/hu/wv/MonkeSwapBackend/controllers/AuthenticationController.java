package hu.wv.MonkeSwapBackend.controllers;

import hu.wv.MonkeSwapBackend.authentication.AuthenticationResponse;
import hu.wv.MonkeSwapBackend.authentication.LoginRequest;
import hu.wv.MonkeSwapBackend.authentication.RegisterRequest;
import hu.wv.MonkeSwapBackend.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @Operation(summary = "Register a user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful registration", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content)
    })
    @PostMapping("/register")
    public void register(
            @RequestBody RegisterRequest request
    ) {
        service.register(request);
    }

    @Operation(summary = "Logs in the user by password and email address")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid credentials supplied", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(service.login(request));
    }
}
