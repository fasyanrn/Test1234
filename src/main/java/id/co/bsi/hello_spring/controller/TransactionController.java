package id.co.bsi.hello_spring.controller;

import id.co.bsi.hello_spring.dto.response.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransactionController {

//    @GetMapping("api/transaction")
//    public ResponseEntity<List<TransactionResponse>> transactionHistory() {
//
//        List<TransactionResponse> transactionList = new ArrayList<>();
//
//        TransactionResponse transactionResponse = new TransactionResponse();
//
//        transactionResponse.setStatus("success");
//        transactionResponse.setMessage("success Get Transaction");
//        transactionResponse.setDateTime("19-03-2023");
//        transactionResponse.setType("Transfer");
//        transactionResponse.setFromTo("aan");
//        transactionResponse.setDescription("transferan");
//        transactionResponse.setAmount(900000);
//
//        TransactionResponse transactionResponse2 = new TransactionResponse();
//
//        transactionResponse2.setStatus("success");
//        transactionResponse2.setMessage("success Get Transaction");
//        transactionResponse2.setDateTime("19-03-2023");
//        transactionResponse2.setType("Transfer");
//        transactionResponse2.setFromTo("aan");
//        transactionResponse2.setDescription("transferan");
//        transactionResponse2.setAmount(900000);
//
//
//        transactionList.add(transactionResponse);
//        transactionList.add(transactionResponse2);
//
//        return ResponseEntity.ok(transactionList);
//
//    }


    @GetMapping("api/transaction")
    public ResponseEntity<?> transactionHistory(@RequestHeader(value = "token", required = false) String token) {

        String dummyToken = "EWUd8X0vAJ/Ox1vx/SgfAg==";
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Token is missing or invalid");
        }

        if (!token.equals(dummyToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Invalid token");
        }

        List<TransactionResponse> transactionList = new ArrayList<>();

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setStatus("success");
        transactionResponse.setMessage("success Get Transaction");
        transactionResponse.setDateTime("19-03-2023");
        transactionResponse.setType("Transfer");
        transactionResponse.setFromTo("aan");
        transactionResponse.setDescription("transferan");
        transactionResponse.setAmount(900000);

        TransactionResponse transactionResponse2 = new TransactionResponse();
        transactionResponse2.setStatus("success");
        transactionResponse2.setMessage("success Get Transaction");
        transactionResponse2.setDateTime("19-03-2023");
        transactionResponse2.setType("Transfer");
        transactionResponse2.setFromTo("aan");
        transactionResponse2.setDescription("transferan");
        transactionResponse2.setAmount(900000);

        transactionList.add(transactionResponse);
        transactionList.add(transactionResponse2);

        return ResponseEntity.ok(transactionList);
    }
}
