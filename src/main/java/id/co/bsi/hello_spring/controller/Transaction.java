package id.co.bsi.hello_spring.controller;

import id.co.bsi.hello_spring.model.TransactionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Transaction {

    @GetMapping("api/transaction")
    public ResponseEntity<TransactionModel> transactionHistory() {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setDateTime("19-03-2023");
        transactionModel.setType("Transfer");
        transactionModel.setFromTo("aan");
        transactionModel.setDescription("transferan");
        transactionModel.setAmount(900000);
        return ResponseEntity.ok(transactionModel);

    }
}
