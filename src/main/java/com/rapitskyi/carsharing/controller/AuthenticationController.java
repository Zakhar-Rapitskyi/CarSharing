package com.rapitskyi.carsharing.controller;


import com.rapitskyi.carsharing.dto.request.AuthenticationRequest;
import com.rapitskyi.carsharing.dto.request.RegisterRequest;
import com.rapitskyi.carsharing.dto.response.AuthenticationResponse;
import com.rapitskyi.carsharing.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid RegisterRequest request
    ){
        authService.register(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }
}