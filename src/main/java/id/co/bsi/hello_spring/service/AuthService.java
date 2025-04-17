package id.co.bsi.hello_spring.service;

import id.co.bsi.hello_spring.dto.request.LoginRequest;
import id.co.bsi.hello_spring.dto.request.RegisterRequest;
import id.co.bsi.hello_spring.dto.response.LoginResponse;
import id.co.bsi.hello_spring.dto.response.RegisterResponse;
import id.co.bsi.hello_spring.model.User;
import id.co.bsi.hello_spring.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegisterResponse register(RegisterRequest request) {
        RegisterResponse response = new RegisterResponse();

        //Cek apakah email sudah ada atau belum
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            response.setStatus("error");
            response.setMessage("Email already registered.");
            return response;
        }

        String passwordHash = hash(request.getPassword());
        String token = hash(request.getEmail() + ":" + request.getPassword());

        User newUser = new User();
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());
        newUser.setPhone(request.getPhone().toString());
        newUser.setPasswordHash(passwordHash);
        newUser.setToken(token);

        userRepository.save(newUser);

        response.setStatus("success");
        response.setMessage("Registration Successful!");
        return response;
    }

    public LoginResponse login(LoginRequest request) {
        LoginResponse response = new LoginResponse();

        //Cek apakah email sudah ada atau belum
        var userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            response.setStatus("error");
            response.setMessage("Email not found.");
            return response;
        }


        //Cek apakah password sudah ada atau belum
        User user = userOpt.get();
        if (!user.getPasswordHash().equals(hash(request.getPassword()))) {
            response.setStatus("error");
            response.setMessage("Incorrect password.");
            return response;
        }

        //Keluarin output jika sukses
        response.setStatus("success");
        response.setToken(user.getToken());
        response.setMessage("Login successful.");
        return response;
    }

    //Sistem hashing
    private String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
