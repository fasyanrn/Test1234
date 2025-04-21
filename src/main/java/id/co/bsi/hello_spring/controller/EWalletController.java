package id.co.bsi.hello_spring.controller;

import id.co.bsi.hello_spring.service.EWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ewallet")
public class EWalletController {

    @Autowired
    private EWalletService eWalletService;

    @GetMapping("/sync")
    public Map<String, Object> syncBalance(@RequestParam String accountnum) {
        int balance = eWalletService.syncBalance(accountnum);
        return Map.of(
                "status", "success",
                "synced_balance", balance
        );
    }

    @GetMapping("/import")
    public List<Map<String, Object>> importTransactions(@RequestParam String accountnum) {
        return eWalletService.importTransactions(accountnum);
    }

    @PostMapping("/transfer")
    public Map<String, Object> transferToEWallet(@RequestParam String accountnum, @RequestParam int amount) {
        String result = eWalletService.transferToEWallet(accountnum, amount);
        return Map.of(
                "status", "success",
                "message", result
        );
    }
}