package id.co.bsi.hello_spring.controller;

import id.co.bsi.hello_spring.dto.request.TransferRequest;
import id.co.bsi.hello_spring.dto.response.RekeningResponse;
import id.co.bsi.hello_spring.dto.response.TransferResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransferController {


    @PostMapping("/api/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest transferRequest) {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setStatus("transfer success");

        return ResponseEntity.ok(transferResponse);
    }

    @GetMapping("/api/rekening")

    public ResponseEntity<?> rekening(@RequestHeader(value = "token", required = false) String token) {
            String dummyToken = "EWUd8X0vAJ/Ox1vx/SgfAg==";

            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Unauthorized: Token is missing or invalid");
            }

            if (!token.equals(dummyToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Unauthorized: Invalid token");
            }
            RekeningResponse rekeningResponse = new RekeningResponse();

        RekeningResponse.RekeningData rekeningData = new RekeningResponse.RekeningData();
        rekeningData.setAccount_number("1239");
        rekeningData.setAccount_name("Jack");

        RekeningResponse.RekeningData rekeningData2 = new RekeningResponse.RekeningData();
        rekeningData2.setAccount_number("125");
        rekeningData2.setAccount_name("Jack 2");

        rekeningResponse.getData().add(rekeningData);
        rekeningResponse.getData().add(rekeningData2);

//        rekeningResponse.setAccount_number("123456");
//        rekeningResponse.setAccount_name("aan");

        return ResponseEntity.ok(rekeningResponse);


    }
}
