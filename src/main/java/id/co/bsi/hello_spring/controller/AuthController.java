package id.co.bsi.hello_spring.controller;

import id.co.bsi.hello_spring.dto.request.LoginRequest;
import id.co.bsi.hello_spring.dto.request.RegisterRequest;
import id.co.bsi.hello_spring.dto.response.LoginResponse;
import id.co.bsi.hello_spring.dto.response.RegisterResponse;
import id.co.bsi.hello_spring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/api/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
