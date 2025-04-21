
package id.co.bsi.hello_spring.controller;

import id.co.bsi.hello_spring.dto.request.TopUpRequest;
import id.co.bsi.hello_spring.dto.response.TopUpResponse;
import id.co.bsi.hello_spring.model.TransactionModel;
import id.co.bsi.hello_spring.model.User;
import id.co.bsi.hello_spring.model.UserPin;
import id.co.bsi.hello_spring.repository.UserPinRepository;
import id.co.bsi.hello_spring.repository.UserRepository;
import id.co.bsi.hello_spring.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Optional;

@RestController
public class TopUpController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPinRepository userPinRepository;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/api/topup")
    public ResponseEntity<TopUpResponse> topup (@RequestBody TopUpRequest topUpRequest, @RequestHeader("token") String token){
        TopUpResponse response = new TopUpResponse();

        Optional<User> userOpt = userRepository.findByAccountnum(topUpRequest.getAccountnum());
        if (userOpt.isEmpty()) {
            response.setStatus("fail");
            response.setMessage("Account not found.");
            return ResponseEntity.status(404).body(response);
        }

        User user = userOpt.get();
        if (!user.getToken().equals(token)) {
            response.setStatus("fail");
            response.setMessage("Unauthorized.");
            return ResponseEntity.status(401).body(response);
        }

        // Validate PIN
        Optional<UserPin> userPinOpt = userPinRepository.findByAccountnum(user.getAccountnum());
        if (userPinOpt.isEmpty()) {
            response.setStatus("fail");
            response.setMessage("PIN not registered.");
            return ResponseEntity.status(401).body(response);
        }

        try {
            String hashedPin = Base64.getEncoder().encodeToString(
                MessageDigest.getInstance("SHA-256").digest(topUpRequest.getPin().getBytes())
            );
            if (!userPinOpt.get().getPinHash().equals(hashedPin)) {
                response.setStatus("fail");
                response.setMessage("Invalid PIN");
                return ResponseEntity.status(401).body(response);
            }
        } catch (Exception e) {
            response.setStatus("fail");
            response.setMessage("PIN validation error.");
            return ResponseEntity.status(500).body(response);
        }

        // Update balance
        user.setBalance(user.getBalance() + topUpRequest.getAmount());
        userRepository.save(user);

        // Add transaction log
        TransactionModel txn = new TransactionModel();
        txn.setAccountnum(user.getAccountnum());
        txn.setAmount(topUpRequest.getAmount());
        txn.setType("income");
        txn.setFromTo("TopUp via " + topUpRequest.getMethod());
        txn.setDescription("Top up successful");
        txn.setDateTime(java.time.LocalDate.now().toString());

        transactionService.save(txn);

        // Prepare response
        response.setStatus("success");
        response.setMessage("Top Up Success");

        TopUpResponse.OptionTopUp data = new TopUpResponse.OptionTopUp();
        data.setAccountnum(user.getAccountnum());
        data.setBalance(user.getBalance());
        response.setData(data);

        return ResponseEntity.ok(response);
    }
}
