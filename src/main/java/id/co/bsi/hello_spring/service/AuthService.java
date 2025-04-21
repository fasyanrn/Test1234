package id.co.bsi.hello_spring.service;

import id.co.bsi.hello_spring.dto.request.LoginRequest;
import id.co.bsi.hello_spring.dto.request.RegisterRequest;
import id.co.bsi.hello_spring.dto.response.LoginResponse;
import id.co.bsi.hello_spring.dto.response.RegisterResponse;
import id.co.bsi.hello_spring.model.User;
import id.co.bsi.hello_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // Variabel untuk menyimpan counter (bisa simpan dalam memori, atau menggunakan DB, kali ini tetap di dalam service)
    private static long counter = 100000; // Mulai dari 100000

    public RegisterResponse register(RegisterRequest req) {
        RegisterResponse res = new RegisterResponse();
        Optional<User> existing = userRepository.findByEmail(req.getEmail());
        if (existing.isPresent()) {
            res.setStatus("error");
            res.setMessage("Email already registered.");
            return res;
        }

        // Cek apakah nomor telepon sudah terdaftar
        Optional<User> existingPhone = userRepository.findByPhone(req.getPhone().toString());
        if (existingPhone.isPresent()) {
            res.setStatus("error");
            res.setMessage("Phone number already registered.");
            return res;
        }

        // Generate accountnum dari counter yang bertambah setiap kali user baru
        String accountnum = String.valueOf(generateAccountNum());

        User user = new User();
        user.setAccountnum(accountnum); // Menggunakan accountnum yang sudah di generate
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone().toString());
        user.setPasswordHash(hash(req.getPassword()));
        user.setToken(hash(req.getEmail() + ":" + req.getPassword()));
        user.setBalance(0);

        userRepository.save(user);

        res.setStatus("success");
        res.setMessage("Registration successful.");
        return res;
    }

    private synchronized long generateAccountNum() {
        counter++; // Increment counter
        return counter; // Return angka baru
    }

    public LoginResponse login(LoginRequest req) {
        LoginResponse res = new LoginResponse();
        Optional<User> userOpt = userRepository.findByEmail(req.getEmail());
        if (userOpt.isEmpty()) {
            res.setStatus("error");
            res.setMessage("Email not found.");
            return res;
        }

        User user = userOpt.get();
        if (!user.getPasswordHash().equals(hash(req.getPassword()))) {
            res.setStatus("error");
            res.setMessage("Incorrect password.");
            return res;
        }

        res.setStatus("success");
        res.setToken(user.getToken());
        res.setAccountnum(user.getAccountnum());
        res.setMessage("Login successful.");
        return res;
    }

    private String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(input.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
