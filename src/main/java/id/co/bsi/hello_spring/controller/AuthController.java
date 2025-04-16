package id.co.bsi.hello_spring.controller;

import id.co.bsi.hello_spring.dto.request.LoginRequest;
import id.co.bsi.hello_spring.dto.request.RegisterRequest;
import id.co.bsi.hello_spring.dto.response.LoginResponse;
import id.co.bsi.hello_spring.dto.response.RegisterResponse;
import id.co.bsi.hello_spring.model.User;
import id.co.bsi.hello_spring.util.UserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;

@RestController
public class AuthController {

    //
    @PostMapping("/api/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        List<User> users = UserUtil.loadUsers();

        //Cek apakah email sudah ada di json (ada di userutil codenya)
        if (UserUtil.findUserByEmail(registerRequest.getEmail()).isPresent()) {
            RegisterResponse response = new RegisterResponse();
            response.setStatus("error");
            response.setMessage("Email already registered.");
            return ResponseEntity.badRequest().body(response);
        }

        //
        String passwordHash = hash(registerRequest.getPassword());
        String token = hash(registerRequest.getEmail() + ":" + registerRequest.getPassword());

        //Set untuk register dan dilempar ke util
        User newUser = new User();
        newUser.setFullName(registerRequest.getFullName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPasswordHash(passwordHash);
        newUser.setPhone(registerRequest.getPhone());
        newUser.setToken(token);

        users.add(newUser);
        UserUtil.saveUsers(users);

        //Output klo registernya aman
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setStatus("success");
        registerResponse.setMessage("Registration Successful!");
        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        //Cek email
        var userOpt = UserUtil.findUserByEmail(loginRequest.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).build();
            
        }

        //Panggil get dan cek jika passwordnya sesuai sama yang di hash
        User user = userOpt.get();
        if (!user.getPasswordHash().equals(hash(loginRequest.getPassword()))) {
            return ResponseEntity.status(401).build();

        }
        //Kalau sukses lanjut
        LoginResponse response = new LoginResponse();
        response.setStatus("success");
        response.setToken(user.getToken());

        //Dapetin tokennya
        return ResponseEntity.ok()
                .header("Token ", user.getToken())
                .body(response);
    }

    //hashing string menggunakan algoritma MD5 lalu mengubah hasilnya ke Base64
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
