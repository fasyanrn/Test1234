
package id.co.bsi.hello_spring.controller;

import id.co.bsi.hello_spring.dto.response.TransactionPageResponse;
import id.co.bsi.hello_spring.model.TransactionModel;
import id.co.bsi.hello_spring.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{accountnum}")
    public ResponseEntity<TransactionPageResponse> getTransactions(
            @RequestHeader("token") String token,
            @PathVariable String accountnum,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Page<TransactionModel> resultPage = transactionService.getFilteredTransactions(token, accountnum, search, page, size, sortBy, direction);

        TransactionPageResponse.DataContent content = new TransactionPageResponse.DataContent();
        content.setTransactions(resultPage.getContent());
        content.setTotalPages(resultPage.getTotalPages());
        content.setCurrentPage(resultPage.getNumber());
        content.setTotalElements(resultPage.getTotalElements());

        TransactionPageResponse res = new TransactionPageResponse();
        res.setStatus("success");
        res.setMessage("List transaksi");
        res.setData(content);

        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<?> postTransaction(
            @RequestHeader("token") String token,
            @RequestBody TransactionModel transaction
    ) {
        TransactionModel saved = transactionService.saveTransaction(token, transaction);
        if (saved == null) {
            return ResponseEntity.status(401).body("Unauthorized or account mismatch");
        }
        return ResponseEntity.ok(saved);
    }
}
