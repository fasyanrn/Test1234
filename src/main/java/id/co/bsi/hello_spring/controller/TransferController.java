
package id.co.bsi.hello_spring.controller;

import id.co.bsi.hello_spring.dto.request.TransferRequest;
import id.co.bsi.hello_spring.dto.response.TransferResponse;
import id.co.bsi.hello_spring.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest transferRequest) {
        TransferResponse transferResponse = transferService.transfer(transferRequest);
        if ("success".equals(transferResponse.getStatus())) {
            return ResponseEntity.ok(transferResponse);
        } else {
            return new ResponseEntity<>(transferResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
