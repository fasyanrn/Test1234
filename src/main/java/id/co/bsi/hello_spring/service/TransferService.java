
package id.co.bsi.hello_spring.service;

import id.co.bsi.hello_spring.dto.request.TransferRequest;
import id.co.bsi.hello_spring.model.TransactionModel;
import id.co.bsi.hello_spring.model.User;
import id.co.bsi.hello_spring.model.UserPin;
import id.co.bsi.hello_spring.repository.TransactionRepository;
import id.co.bsi.hello_spring.repository.UserPinRepository;
import id.co.bsi.hello_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;

@Service
public class TransferService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserPinRepository userPinRepository;

    public String transfer(TransferRequest request) {
        Optional<User> fromUserOpt = userRepository.findByAccountnum(request.getFromAccountnum());
        Optional<User> toUserOpt = userRepository.findByAccountnum(request.getToAccountnum());

        if (fromUserOpt.isEmpty() || toUserOpt.isEmpty()) {
            return "Account not found";
        }

        User fromUser = fromUserOpt.get();
        User toUser = toUserOpt.get();

        Optional<UserPin> pinOpt = userPinRepository.findByAccountnum(fromUser.getAccountnum());
        if (pinOpt.isEmpty()) {
            return "PIN not registered";
        }

        try {
            String hashedPin = Base64.getEncoder().encodeToString(
                MessageDigest.getInstance("SHA-256").digest(request.getPin().getBytes())
            );
            if (!pinOpt.get().getPinHash().equals(hashedPin)) {
                return "Invalid PIN";
            }
        } catch (Exception e) {
            return "PIN validation error";
        }

        if (fromUser.getBalance() < request.getAmount()) {
            return "Insufficient balance";
        }

        // Update balances
        fromUser.setBalance(fromUser.getBalance() - request.getAmount());
        toUser.setBalance(toUser.getBalance() + request.getAmount());
        userRepository.save(fromUser);
        userRepository.save(toUser);

        // Create transactions
        TransactionModel senderTxn = new TransactionModel();
        senderTxn.setAccountnum(fromUser.getAccountnum());
        senderTxn.setAmount(request.getAmount());
        senderTxn.setDateTime(LocalDate.now().toString());
        senderTxn.setFromTo("To: " + toUser.getAccountnum());
        senderTxn.setDescription(request.getDescription());
        senderTxn.setType("expense");

        TransactionModel receiverTxn = new TransactionModel();
        receiverTxn.setAccountnum(toUser.getAccountnum());
        receiverTxn.setAmount(request.getAmount());
        receiverTxn.setDateTime(LocalDate.now().toString());
        receiverTxn.setFromTo("From: " + fromUser.getAccountnum());
        receiverTxn.setDescription(request.getDescription());
        receiverTxn.setType("income");

        transactionRepository.save(senderTxn);
        transactionRepository.save(receiverTxn);

        return "Transfer successful";
    }
}
